# Database Schema Documentation

## List of Relations

| Schema | Name          | Type  | Owner    |
|--------|---------------|-------|----------|
| public | file          | table | postgres |
| public | fileevent     | table | postgres |
| public | network       | table | postgres |
| public | networkevent  | table | postgres |
| public | process       | table | postgres |
| public | processevent  | table | postgres |

---

## Table: `public.file`

### Columns

| Column       | Type   | Nullable |
|--------------|--------|----------|
| id           | integer| not null |
| ownergroupid | text   |          |
| hostname     | text   | not null |
| name         | text   |          |
| owneruserid  | text   |          |
| path         | text   | not null |

### Indexes

- `file_pkey`: PRIMARY KEY, btree (id)
- `file_id`: btree (id)
- `file_name`: btree (name)
- `file_path`: btree (path)

---

## Table: `public.process`

### Columns

| Column       | Type   | Nullable |
|--------------|--------|----------|
| id           | integer| not null |
| exename      | text   |          |
| exepath      | text   |          |
| ownergroupid | text   |          |
| hostname     | text   | not null |
| pid          | integer| not null |
| owneruserid  | text   |          |
| cmdline      | text   |          |

### Indexes

- `process_pkey`: PRIMARY KEY, btree (id)
- `process_exename`: btree (exename)
- `process_exepath`: btree (exepath)
- `process_id`: btree (id)

---

## Table: `public.network`

### Columns

| Column  | Type   | Nullable |
|---------|--------|----------|
| id      | integer| not null |
| dstip   | cidr   |          |
| dstport | integer|          |
| hostname| text   | not null |
| srcip   | cidr   |          |
| srcport | integer|          |

### Indexes

- `network_pkey`: PRIMARY KEY, btree (id)
- `network_dstip`: btree (dstip)
- `network_id`: btree (id)
- `network_srcip`: btree (srcip)

---

## Table: `public.fileevent`

### Columns

| Column   | Type         | Nullable |
|----------|--------------|----------|
| id       | bigint       | not null |
| amount   | bigint       |          |
| dstid    | integer      | not null |
| endtime  | numeric(19,9)|          |
| eventno  | bigint       | not null |
| hostname | text         | not null |
| optype   | text         |          |
| srcid    | integer      | not null |
| starttime| numeric(19,9)|          |

### Indexes

- `fileevent_pkey`: PRIMARY KEY, btree (id)
- `fileevent_dstid`: btree (dstid)
- `fileevent_id`: btree (id)
- `fileevent_srcid`: btree (srcid)

---

## Table: `public.processevent`

### Columns

| Column   | Type         | Nullable |
|----------|--------------|----------|
| id       | bigint       | not null |
| dstid    | integer      | not null |
| endtime  | numeric(19,9)|          |
| eventno  | bigint       | not null |
| hostname | text         | not null |
| optype   | text         |          |
| srcid    | integer      | not null |
| starttime| numeric(19,9)|          |

### Indexes

- `processevent_pkey`: PRIMARY KEY, btree (id)
- `processevent_dstid`: btree (dstid)
- `processevent_id`: btree (id)
- `processevent_srcid`: btree (srcid)

---

## Table: `public.networkevent`

### Columns

| Column   | Type         | Nullable |
|----------|--------------|----------|
| id       | bigint       | not null |
| amount   | bigint       |          |
| dstid    | integer      | not null |
| endtime  | numeric(19,9)|          |
| eventno  | bigint       | not null |
| hostname | text         | not null |
| optype   | text         |          |
| srcid    | integer      | not null |
| starttime| numeric(19,9)|          |

### Indexes

- `networkevent_pkey`: PRIMARY KEY, btree (id)
- `networkevent_dstid`: btree (dstid)
- `networkevent_id`: btree (id)
- `networkevent_srcid`: btree (srcid)
