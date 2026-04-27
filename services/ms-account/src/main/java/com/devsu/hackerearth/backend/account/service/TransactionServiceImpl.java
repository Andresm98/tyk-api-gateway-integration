package com.devsu.hackerearth.backend.account.service;

import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.account.infrastructure.client.ClientConsumer;
import com.devsu.hackerearth.backend.account.infrastructure.exception.DontDeleteTransactionsException;
import com.devsu.hackerearth.backend.account.infrastructure.exception.InsufficientBalanceException;
import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.Transaction;
import com.devsu.hackerearth.backend.account.model.dto.BankStatementDto;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;
import com.devsu.hackerearth.backend.account.repository.AccountRepository;
import com.devsu.hackerearth.backend.account.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final ClientConsumer clientConsumer;
    private final NotificationService notificationService;

    public TransactionServiceImpl(NotificationService notificationService, TransactionRepository transactionRepository,
            AccountRepository accountRepository, ClientConsumer clientConsumer) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.clientConsumer = clientConsumer;
        this.notificationService = notificationService;
    }

    @Override
    public List<TransactionDto> getAll() {
        return transactionRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public TransactionDto getById(Long id) {
        return transactionRepository.findById(id)
                .map(this::mapToDto)
                .orElseThrow(() -> new RuntimeException("Transacción no encontrada"));
    }

    @Override
    @Transactional
    public TransactionDto create(TransactionDto transactionDto) {
        Account account = accountRepository.findById(transactionDto.getAccountId())
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        double currentBalance = account.getInitialAmount();
        double newBalance = currentBalance + transactionDto.getAmount();

        if (!account.isActive()) {
            throw new RuntimeException("Cuenta desactivada!!");
        }

        if (newBalance < 0) {
            throw new InsufficientBalanceException("Saldo no disponible");
        }

        account.setInitialAmount(newBalance);
        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setDate(new java.util.Date());

        transaction.setType(transactionDto.getAmount() >= 0 ? "DEPOSITO" : "RETIRO");
        transaction.setAmount(transactionDto.getAmount());
        transaction.setBalance(newBalance);
        transaction.setAccount(account);

        Transaction saved = transactionRepository.save(transaction);

        notificationService.notifyClientService(account.getClientId(), transaction.getAmount());

        return mapToDto(saved);
    }

    private TransactionDto mapToDto(Transaction transaction) {
        return new TransactionDto(
                transaction.getId(),
                transaction.getDate(),
                transaction.getType(),
                transaction.getAmount(),
                transaction.getBalance(),
                transaction.getAccount().getId());
    }

    @Override
    public List<BankStatementDto> getAllByAccountClientIdAndDateBetween(
            Long clientId, Date dateTransactionStart, Date dateTransactionEnd) {

        String clientName = clientConsumer.getClientName(clientId);

        java.time.ZoneId utc = java.time.ZoneId.of("UTC");

        java.time.LocalDateTime start = dateTransactionStart.toInstant()
                .atZone(utc).toLocalDateTime()
                .withHour(0).withMinute(0).withSecond(0).withNano(0);

        java.time.LocalDateTime end = dateTransactionEnd.toInstant()
                .atZone(utc).toLocalDateTime()
                .withHour(23).withMinute(59).withSecond(59).withNano(999999999);

        Date finalStart = Date.from(start.atZone(utc).toInstant());
        Date finalEnd = Date.from(end.atZone(utc).toInstant());

        List<Transaction> transactions = transactionRepository
                .findByAccountClientIdAndDateBetween(clientId, finalStart, finalEnd);

        return transactions.stream().map(t -> {
            BankStatementDto dto = new BankStatementDto();
            dto.setDate(t.getDate());
            dto.setClient(clientName);
            dto.setAccountNumber(t.getAccount().getNumber());
            dto.setAccountType(t.getAccount().getType());
            dto.setInitialAmount(t.getBalance() - t.getAmount());
            dto.setTransactionType(t.getType());
            dto.setIsActive(t.getAccount().isActive());
            dto.setAmount(t.getAmount());
            dto.setBalance(t.getBalance());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public TransactionDto getLastByAccountId(Long accountId) {
        return null;
    }

    @Override
    public TransactionDto deleteById(Long id) {
        throw new DontDeleteTransactionsException(
                "Las transacciones financieras no pueden ser eliminadas por motivos de auditoría.");
    }

}
