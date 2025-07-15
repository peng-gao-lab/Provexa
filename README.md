<div align="center">
  <img src="./docs/logo/provexa-logo.png" alt="Logo" width="200">
  <h1 align="center">Provexa: Enabling Efficient Attack Investigation via Human-in-the-Loop Security Analysis</h1>
</div>

<p align="center">
  <a href="https://opensource.org/licenses/MIT"><img src="https://img.shields.io/badge/License-MIT-lavender.svg" alt="License: MIT"></a>
  <a href='https://provexa-app.github.io'><img src='https://img.shields.io/badge/Project-Website-green'></a>
  <a href='https://arxiv.org/pdf/2211.05403'><img src='https://img.shields.io/badge/Paper-Arxiv-crimson'></a>
</p>


## Environment Setup

Provexa is used to carry out attack investigation from collected system audit logs. It first builds the system provenance data, saves it to a database and provides a Domain-Specific Language (DSL) called `ProvQL` to carry out the investigation. This repo supports two databses - PostgreSQL and Neo4j. 

1. Install the database of your choice.
* **PostgreSQL**: Once you install it, use the [config file](cfg/postgres.properties) to setup the following variables:

```
url=jdbc:postgresql://127.0.0.1:5432/ 
dbName=test
username=postgres
password=postgres
```

- If you used the default installation, the `url` should be left as is. 
- The `dbName` will be used to create your database when parsing your logs and saving it to the database.
- `username` and `password` are the credentials you set up when installing Postgres. 



This repo supports audit logs collected from `Sysdig`. Read more here on how to setup and use [Sysdig](docs/sysdig.md).

## Quick Start

Once the database is populated with the logs, you can use the Provexa WebUI to query the database and investigate the logs.

1. Start the WebUI

```bash
cd frontend
npm start

# if the above fails due to openssl compatibility, try this:
# npm run start-legacy
```

2. Start the backend server

With default settings, it will use port 8080, Postgres database and Optimized scheduler. 
```bash
mvn -q exec:java -Dexec.mainClass=main.WebMain
```

You can pass arguments to set either of these paramets. Supported databases are Postgres and Neo4j. Supported modes are O(ptimized), that uses our scheduler engine and R(ecursive) that does not use our scheduler engine.

```bash
# Use Neo4j database with custom port and recursive mode
mvn -q exec:java -Dexec.mainClass=main.WebMain -Dexec.args="--port=9090 --mode=r --db=neo4j"

# Use default settings but with Neo4j
mvn -q exec:java -Dexec.mainClass=main.WebMain -Dexec.args="--db=neo4j"

# Custom port only
mvn -q exec:java -Dexec.mainClass=main.WebMain -Dexec.args="--port=9090"

# Show help
mvn -q exec:java -Dexec.mainClass=main.WebMain -Dexec.args="--help"
```

3. Navigate to the webUI and start writing your ProvQL queries to start the investigation
