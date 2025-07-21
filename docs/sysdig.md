# Sysdig

## What is Sysdig?

Sysdig is an open-source, system-level exploration and troubleshooting tool. It provides deep visibility into system activities by capturing and analyzing system calls and events. Sysdig is widely used for performance monitoring, security analysis, and debugging in Linux environments.

### Key Features:
- Real-time system activity monitoring.
- Advanced filtering and scripting capabilities.
- Support for containerized environments.
- Integration with tools like Kubernetes and Docker.

---

## How to Use Sysdig

### Installation

This guide is tested on sysdig version 0.36.0+repack. To install Sysdig on a Linux system:

**A. Uninstall Existing Sysdig (if any):**
```bash
sudo apt-get remove --purge sysdig
```

**B. Install Prerequisites:**
```bash
sudo apt-get update
sudo apt-get install -y curl apt-transport-https lsb-release gnupg
```

**C. Add Sysdig’s GPG Key and Repository:**
```bash
curl -s https://download.sysdig.com/DRAIOS-GPG-KEY.public | sudo apt-key add -
DISTRO=$(lsb_release -cs)
echo "deb https://download.sysdig.com/stable/deb/ $(lsb_release -cs) sysdig" | sudo tee /etc/apt/sources.list.d/draios.list
sudo apt-get update
```

**D. Install Sysdig:**
```bash
sudo apt-get install sysdig
```

---

## Save Sysdig Output to a Text File

Use the following command to run Sysdig and output logs in a custom format aligned with our parsing module. Save the generated logs file in a directory for later use. 

```bash
sudo sysdig -p "%evt.num %evt.rawtime.s.%evt.rawtime.ns %evt.cpu %proc.name (%proc.pid) %evt.dir %evt.type cwd=%proc.cwd %evt.args latency=%evt.latency exepath=%proc.exepath cmd= %proc.exeline"  "proc.name!=tmux and (evt.type=read or evt.type=readv or evt.type=write or evt.type=writev or evt.type=fcntl or evt.type=accept or evt.type=execve or evt.type=clone or evt.type=pipe or evt.type=rename or evt.type=sendmsg or evt.type=recvmsg) and proc.name!=sysdig" > output_file.txt
```

### Explanation of the Command:
- `-p`: Specifies the custom output format.
- `%evt.num`: Event number.
- `%evt.rawtime.s.%evt.rawtime.ns`: Event timestamp in seconds and nanoseconds.
- `%evt.cpu`: CPU where the event occurred.
- `%proc.name`: Process name.
- `%proc.pid`: Process ID.
- `%evt.dir`: Event direction (e.g., `>` for outgoing, `<` for incoming).
- `%evt.type`: Event type (e.g., `read`, `write`).
- `%proc.cwd`: Current working directory of the process.
- `%evt.args`: Arguments of the event.
- `%evt.latency`: Latency of the event.
- `%proc.exepath`: Executable path of the process.
- `%proc.exeline`: Command line used to start the process.

### Filters:
- `proc.name!=tmux`: Exclude events from the `tmux` process.
- `evt.type=...`: Include specific event types (e.g., `read`, `write`, `execve`).
- `proc.name!=sysdig`: Exclude events from the `sysdig` process itself.

### Output:
The command saves the filtered and formatted output to `output_file.txt`.

A sample Sysdig output file looks like [this](../examples/sysdig/multistep_supply_chain/multistep_supply_chain.txt)

---

## Parsing Generated Log

Once the logs are generated, they can be passed to our parser to build the provenance graph based on the captured system audit logs. The parser supports multiple databases and configurable options.

### Available Arguments:

- `--logfile=<path>`: Path to the sysdig log file to process (default: examples/sysdig/multistep_supply_chain/multistep_supply_chain.txt)
- `--db=<database>`: Set database type: 'postgres' or 'neo4j' (default: postgres)
- `--dbname=<name>`: Database name for Neo4j (default: raptor, only used with --db=neo4j)
- `--help, -h`: Show help message with usage information

### Usage Examples:

**Parse logs with default settings (PostgreSQL database):**
For postgres, you should specify the database name you want in the [config file](../cfg/db.properties). This file should be created by copying the [example file](../cfg/db.properties.example). 

```bash
mvn -q exec:java -Dexec.mainClass=main.SysdigMain -Dexec.args="--logfile=output_file.txt"
```

**Parse logs into Neo4j database:**
For Neo4j, the database name should be provided with the flag `--dbname`. By default, it creates a databse with the name `testing`. 

```bash
mvn -q exec:java -Dexec.mainClass=main.SysdigMain -Dexec.args="--db=neo4j --dbname=investigation --logfile=output_file.txt"
```

**Show available options:**
```bash
mvn -q exec:java -Dexec.mainClass=main.SysdigMain -Dexec.args="--help"
```
