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
