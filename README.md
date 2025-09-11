<div align="center">
  <img src="./docs/logo/provexa-logo.png" alt="Logo" width="200">
  <h1 align="center">Provexa: Enabling Efficient Attack Investigation via Human-in-the-Loop Security Analysis</h1>
</div>

<p align="center">
  <a href='https://www.vldb.org/pvldb/vol18/p3771-gao.pdf'><img src='https://img.shields.io/badge/Paper-VLDB-crimson'></a>
  <a href='https://provexa-app.github.io'><img src='https://img.shields.io/badge/Project-Website-green'></a>
  <a href="https://opensource.org/licenses/MIT"><img src="https://img.shields.io/badge/License-MIT-lavender.svg" alt="License: MIT"></a>
</p>

## Overview

Provexa is used to carry out attack investigation from collected system audit logs. It first builds the system provenance data, saves it to a database and provides a Domain-Specific Language (DSL) called `ProvQL` to carry out the investigation.
<div>


Our research paper has been accepted for publication at the <a href="https://vldb.org/2025/" target="_blank"> 51st International Conference on Very Large Databases (VLDB 2025)</a>.

</div>

## Demo Video

Watch the demo video to learn how to use Provexa for investigation using the WebUI and executing ProvQL queries:

<p align="center">
  <a href="https://youtu.be/onPvk2F8GIM" target="_blank">
    <img src="https://img.youtube.com/vi/onPvk2F8GIM/0.jpg" alt="Provexa Demo Video" width="600">
  </a>
</p>

---

## Environment Setup

### 1. Prerequisites

Install Java 11. This repo is tested with openjdk 11.0.27.

### 2. Configuration Setup

Copy the example config to setup Postgres and Neo4j configurations:

```bash
cp cfg/db.properties.example cfg/db.properties
```

### 3. Database Installation

Choose and install your preferred database:

#### A. PostgreSQL Setup

Install PostgreSQL from the [Apt Repository](https://www.postgresql.org/download/linux/ubuntu/). Once installed, configure the following settings inside `cfg/db.properties` in the Postgres configs section:

```
url=jdbc:postgresql://127.0.0.1:5432/ 
dbName=test
username=postgres
password=postgres
```

**Configuration Notes:**
- If you used the default installation, the `url` should be left as is
- The `dbName` will be used as the database name when parsing your logs and saving it to the Postgres database
- `username` and `password` are the credentials you set up when installing Postgres

#### B. Neo4j Setup

A detailed guide on how to install and configure Neo4j database is provided [here](docs/neo4j.md).

### 4. Log Generation

This repo supports audit logs collected from `Sysdig`. Generate logs in a custom format aligned with our parsing module:

```bash
sudo sysdig -p "%evt.num %evt.rawtime.s.%evt.rawtime.ns %evt.cpu %proc.name (%proc.pid) %evt.dir %evt.type cwd=%proc.cwd %evt.args latency=%evt.latency exepath=%proc.exepath cmd= %proc.exeline"  "proc.name!=tmux and (evt.type=read or evt.type=readv or evt.type=write or evt.type=writev or evt.type=fcntl or evt.type=accept or evt.type=execve or evt.type=clone or evt.type=pipe or evt.type=rename or evt.type=sendmsg or evt.type=recvmsg) and proc.name!=sysdig" > output_file.txt
```

### 5. Log Parsing and Database Population

Parse the generated logs and populate them to your database:

```bash
mvn -q exec:java -Dexec.mainClass=main.SysdigMain -Dexec.args="--logfile=output_file.txt"
```

> **Note**: A detailed guide on how to setup Sysdig, commandline arguments you can pass and other details is provided [here](docs/sysdig.md).

## Start Investigation

Once the database is populated with the logs, you can use the Provexa WebUI to query the database and investigate the logs.

#### 1. Start the WebUI

```bash
cd frontend
npm install  # For first run only
npm start

# if the above fails due to openssl compatibility, try this:
# npm run start-legacy
```

#### 2. Backend Server

Start the backend server with default settings (port 8080 and Postgres database):

```bash
mvn -q exec:java -Dexec.mainClass=main.WebMain
```

You can pass arguments to customize the server settings. Supported databases are Postgres and Neo4j.

```bash
# Use Neo4j database with custom port
mvn -q exec:java -Dexec.mainClass=main.WebMain -Dexec.args="--port=9090 --db=neo4j"

# Use default settings but with Neo4j
mvn -q exec:java -Dexec.mainClass=main.WebMain -Dexec.args="--db=neo4j"

# Show help
mvn -q exec:java -Dexec.mainClass=main.WebMain -Dexec.args="--help"
```

#### 3. Begin Investigation

Navigate to the WebUI and start writing your ProvQL queries to begin the investigation. ProvQL queries used in the demo video are provided as an [exampe](examples/sysdig/multistep_supply_chain/provql_queries.txt).

### Command-Line Investigation (Without WebUI)
<hr>

You can execute ProvQL queries directly from the command line without using the WebUI. 

#### 1. Start the backend server

Start the backend server (see Backend Server section in step 2 above).

#### 2. Running Queries

Execute queries using the following command. By default, it uses an example [input file](examples/no-web-ui/input_query.txt):

```bash
mvn -q exec:java -Dexec.mainClass=main.ExecutorMain
```

Use the help option to see how to pass custom input files or use Neo4j database:

```bash
mvn -q exec:java -Dexec.mainClass=main.ExecutorMain -Dexec.args="--help"
```

## Citation
If you use our tool, or find it helpful for your research, please cite us using:

```bibtex
@article{tsegai2025provexa,
    author = {Tsegai, Saimon Amanuel and Yang, Xinyu and Liu, Haoyuan and Gao, Peng},
    title = {Enabling Efficient Attack Investigation via Human-in-the-Loop Security Analysis},
    year = {2025},
    issue_date = {July 2025},
    journal = {Proceedings of the VLDB Endowment},
    volume = {18},
    number = {11},
    doi = {10.14778/3749646.3749653},
    pages = {3771–3783},
}
```
