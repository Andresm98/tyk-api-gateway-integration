# Stage 1: Build dependencies
FROM python:3.11-slim AS builder
WORKDIR /app
RUN pip install flask

# Stage 2: Runtime
FROM python:3.11-slim
WORKDIR /app
COPY --from=builder /usr/local/lib/python3.11/site-packages /usr/local/lib/python3.11/site-packages
COPY . .
EXPOSE 5000
CMD ["python", "app.py"]