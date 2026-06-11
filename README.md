# Nano URL

# To-Do:

- [ ] Latency
- [ ] Throughput
- [ ] Error rate
- [ ] Tests
- [ ] Caching (Redis/Valkey)
- [ ] Rate limiting
- [ ] Link analytics
- [ ] In-memory buffer analytics
- [ ] Containerization
- [ ] Docker Compose
- [ ] CI/CD
- [ ] API Gateway
- [ ] Separate services
- [ ] Cloud deployment
- [ ] Link expiration
- [x] Swap out H2 with MySQL
- [x] Fix `SecureRandom` way of generating short URLs

# MySQL Database Schema:

```sql
CREATE TABLE url (
    uid INT PRIMARY KEY AUTO_INCREMENT, 
    short_url VARCHAR(6) NOT NULL UNIQUE, 
    long_url TEXT NOT NULL, 
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 
    UNIQUE INDEX idx_unique_long_url (long_url(255)), 
    INDEX idx_unique_short_url (short_url)
);
```

# Metrics

- 10th June 2026
  - Threads: 10
  - Ramp-up period: 10 seconds
  - Duration: 120 seconds
  - Before database indexing on `shortUrl` and `longUrl`

| Label | #Samples | FAIL | Error % | Average | Min | Max | Median | 90th pct | 95th pct | 99th pct | Transactions/s | Received | Sent |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **Total** | **67305** | **0** | **0.00%** | **17.09** | **0** | **247** | **24.00** | **40.00** | **42.00** | **47.00** | **560.94** | **105.84** | **116.68** |
| GET /api/url/{shortUrl} | 33648 | 0 | 0.00% | 8.25 | 0 | 73 | 10.00 | 14.00 | 15.00 | 18.00 | 281.11 | 52.83 | 44.74 |
| POST /api/url/shorten | 33657 | 0 | 0.00% | 25.92 | 3 | 247 | 31.00 | 40.00 | 42.00 | 48.00 | 280.51 | 53.14 | 72.04 |

  - Threads: 10
  - Ramp-up period: 10 seconds
  - Duration: 120 seconds
  - After database indexing on `shortUrl` and `longUrl`

| Label | #Samples | FAIL | Error % | Average | Min | Max | Median | 90th pct | 95th pct | 99th pct | Transactions/s | Received | Sent |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **Total** | **255772** | **0** | **0.00%** | **4.47** | **0** | **242** | **4.00** | **8.00** | **9.00** | **14.00** | **2132.16** | **402.28** | **443.49** |
| GET /api/url/{shortUrl} | 127884 | 0 | 0.00% | 1.76 | 0 | 66 | 2.00 | 2.00 | 3.00 | 3.00 | 1068.39 | 200.76 | 170.05 |
| POST /api/url/shorten | 127888 | 0 | 0.00% | 7.18 | 2 | 242 | 7.00 | 9.00 | 11.00 | 17.00 | 1066.10 | 201.96 | 273.81 |

  - Threads: 20
  - Ramp-up period: 10 seconds
  - Duration: 120 seconds

| Label | #Samples | FAIL | Error % | Average | Min | Max | Median | 90th pct | 95th pct | 99th pct | Transactions/s | Received | Sent |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **Total** | **245757** | **0** | **0.00%** | **9.33** | **0** | **256** | **11.00** | **20.00** | **24.00** | **36.00** | **2048.61** | **386.52** | **426.11** |
| GET /api/url/{shortUrl} | 122874 | 0 | 0.00% | 6.01 | 0 | 116 | 6.00 | 10.00 | 12.00 | 20.00 | 1026.64 | 192.92 | 163.40 |
| POST /api/url/shorten | 122883 | 0 | 0.00% | 12.65 | 4 | 256 | 13.00 | 20.00 | 25.00 | 38.00 | 1024.34 | 194.05 | 263.09 |

  - Threads: 30
  - Ramp-up period: 10 seconds
  - Duration: 120 seconds

| Label | #Samples | FAIL | Error % | Average | Min | Max | Median | 90th pct | 95th pct | 99th pct | Transactions/s | Received | Sent |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **Total** | **219203** | **0** | **0.00%** | **15.70** | **0** | **481** | **14.00** | **20.00** | **23.00** | **32.00** | **1827.22** | **344.75** | **380.07** |
| GET /api/url/{shortUrl} | 109597 | 0 | 0.00% | 12.03 | 0 | 481 | 11.00 | 15.00 | 17.00 | 23.00 | 915.58 | 172.05 | 145.73 |
| POST /api/url/shorten | 109606 | 0 | 0.00% | 19.38 | 3 | 477 | 17.00 | 23.00 | 26.00 | 36.00 | 913.65 | 173.08 | 234.66 |

  - Threads: 40
  - Ramp-up period: 10 seconds
  - Duration: 120 seconds

| Label | #Samples | FAIL | Error % | Average | Min | Max | Median | 90th pct | 95th pct | 99th pct | Transactions/s | Received | Sent |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **Total** | **250473** | **0** | **0.00%** | **18.34** | **1** | **280** | **18.00** | **26.00** | **31.00** | **42.00** | **2087.81** | **393.92** | **434.27** |
| GET /api/url/{shortUrl} | 125225 | 0 | 0.00% | 15.03 | 1 | 236 | 15.00 | 20.00 | 26.00 | 33.00 | 1046.30 | 196.61 | 166.53 |
| POST /api/url/shorten | 125248 | 0 | 0.00% | 21.64 | 3 | 280 | 21.00 | 29.00 | 34.00 | 44.00 | 1044.00 | 197.77 | 268.14 |

  - Threads: 50
  - Ramp-up period: 10 seconds
  - Duration: 120 seconds

| Label | #Samples | FAIL | Error % | Average | Min | Max | Median | 90th pct | 95th pct | 99th pct | Transactions/s | Received | Sent |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **Total** | **251340** | **0** | **0.00%** | **22.85** | **0** | **222** | **23.00** | **31.00** | **38.00** | **51.00** | **2094.87** | **395.25** | **435.74** |
| GET /api/url/{shortUrl} | 125655 | 0 | 0.00% | 19.48 | 0 | 210 | 19.00 | 26.00 | 34.00 | 47.00 | 1047.76 | 196.89 | 166.77 |
| POST /api/url/shorten | 125685 | 0 | 0.00% | 26.22 | 2 | 222 | 25.00 | 35.00 | 43.00 | 61.00 | 1047.56 | 198.45 | 269.05 |

    - Threads: 60
    - Ramp-up period: 10 seconds
    - Duration: 120 seconds

