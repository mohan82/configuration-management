



Step1 :Login to PSQL shell as Postgres and Create Database
1) su postgres
2) psql
3) create database cm;
4)\q

Step 2: Create User CM from your *nix shell

createuser  -P  -e cm

Step 3 : Create TableSpace var/lib/postgresql

sudo mkdir -p /var/lib/postgresql/9.1/data/configurationmanagement

Step 4: Changing Ownership to Postgres

sudo chown -R postgres:postgres /var/lb/postgresql/9.1/data/configurationmanagement

Step 5: Create TableSpace
#Copy tablespaces.sql to /tmp

sudo psql -d postgres -U postgres -f /tmp/tablespaces.sql


psql cm -U cm -f create_schema_postgres.sql


