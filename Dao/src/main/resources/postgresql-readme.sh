

## Create CM User

createuser  -P  -e configurationmanagement


##Create TableSpace

echo " Creating directory in varlib postgresql"

sudo mkdir -p /var/lib/postgresql/9.1/data/configurationmanagement

echo  " Changing Ownership to Postgres"

sudo chown -R postgres:postgres /var/lib/postgresql/9.1/data/configurationmanagement

echo  " Switching to postgres and executing tablespaces.sql from /tmp/"
sudo psql -d postgres -U postgres -f /tmp/tablespaces.sql


