package com.devsu.hackerearth.backend.account.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.ExternalClientDto;
import com.devsu.hackerearth.backend.account.model.dto.PartialAccountDto;
import com.devsu.hackerearth.backend.account.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<AccountDto> getAll() {
        List<Account> accounts = accountRepository.findAll();

        return accounts.stream()
                .map(account -> new AccountDto(
                        account.getId(),
                        account.getNumber(),
                        account.getType(),
                        account.getInitialAmount(),
                        account.isActive(),
                        account.getClientId()))
                .collect(Collectors.toList());
    }

    @Override
    public AccountDto getById(Long id) {
        return accountRepository.findById(id)
                .map(this::mapToDtoAccount)
                .orElse(null);
    }

    @Override
    public AccountDto create(AccountDto accountDto) {
        Account account = new Account();
        account.setNumber(accountDto.getNumber());
        account.setType(accountDto.getType());
        account.setInitialAmount(accountDto.getInitialAmount());
        account.setActive(accountDto.isActive());
        account.setClientId(accountDto.getClientId());

        Account savedAccount = accountRepository.save(account);

        accountDto.setId(savedAccount.getId());
        return accountDto;
    }

    @Override
    public AccountDto update(AccountDto accountDto) {
        return accountRepository.findById(accountDto.getId())
                .map(account -> {
                    account.setNumber(accountDto.getNumber());
                    account.setType(accountDto.getType());
                    account.setInitialAmount(accountDto.getInitialAmount());
                    account.setActive(accountDto.isActive());
                    account.setClientId(accountDto.getClientId());
                    Account saved = accountRepository.save(account);
                    return mapToDtoAccount(saved);
                }).orElse(null);
    }

    @Override
    public AccountDto partialUpdate(Long id, PartialAccountDto partialAccountDto) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        account.setActive(partialAccountDto.isIsActive());
        return mapToDtoAccount(accountRepository.save(account));
    }

    private AccountDto mapToDtoAccount(Account c) {
        return new AccountDto(
                c.getId(),
                c.getNumber(),
                c.getType(),
                c.getInitialAmount(),
                c.isActive(),
                c.getClientId());
    }

    @Override
    public void deleteById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        account.setActive(false);
        accountRepository.save(account);
    }

    private ExternalClientDto mapToDto(ExternalClientDto c) {
        return new ExternalClientDto(c.getId(), c.getName());
    }

}
