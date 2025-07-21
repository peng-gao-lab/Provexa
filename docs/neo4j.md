# Installing Neo4j on Linux

We recommend installing Neo4j version 4.4, as it has been tested for compatibility with our setup.

## Option 1: Install Neo4j via Tarball

This method is ideal for local testing, running Neo4j as a regular user, and avoiding system-wide interference.

### 1. Download and extract the tarball

Moving the directory to `~/neo4j` is a safe and organized setup. If you chose to move it to a different directory, remember to provide that in step 3.

```bash
wget https://dist.neo4j.org/neo4j-community-4.4.44-unix.tar.gz
tar -xvzf neo4j-community-4.4.44-unix.tar.gz
mv neo4j-community-4.4.44 ~/neo4j
```

### 2. Start Neo4j

Navigate to the Neo4j directory and start the server:

```bash
cd ~/neo4j
./bin/neo4j start
```
This command will start Neo4j and your installation is successful. 

### 3. Specify Neo4j Home Dir

Use the [db config](../cfg/db.properties) file created by copying the [db example](../cfg/db.properties.example) and specify the `homeDir` variable in the Neo4j Configs section. If you used the guide above to move to the `~/neo4j` directory, you should put your username in `<user>`. If you chose a different directory, put it here. 

```properties
homeDir=/home/<user>/neo4j
```


## Option 2: Install Neo4j via Tarball

This method will require sudo access when starting/stoping neo4j, populating your Sysdig logs or executing ProvQL queries from the command line. 

### 1. Install Neo4j

Visit the official Neo4j Debian repository guide: [https://debian.neo4j.com/](https://debian.neo4j.com/) to install Neo4j.

### 2. Start Neo4j

Since the installation from the apt repository stores files in the `/var/` directory, elevated access may be required to start Neo4j:

```bash
sudo neo4j start
```

**Note:** When using Sysdig to parse and populate your Neo4j database, remember to stop database to avoid lock file conflicts.

```bash
sudo neo4j stop
```

### 3. Specify Neo4j Home Dir

Use the [db config](../cfg/db.properties) file created by copying the [db example](../cfg/db.properties.example) file and specify the `homeDir` variable in the Neo4j Configs section. If you used this method, the home dir value should be:

```properties
homeDir=/var/lib/neo4j/
```