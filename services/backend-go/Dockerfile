# Stage 1: Build
FROM golang:1.21-alpine AS builder
WORKDIR /app
COPY . .
RUN go mod init my-service && go build -o main .

# Stage 2: Runtime
FROM scratch
COPY --from=builder /app/main /main
EXPOSE 8081
ENTRYPOINT ["/main"]