13)What roles and access does the program have?

Program has JDBC connection with two types of groups: OWNER, CLIENT.
This data is stored in database, member table, group_name field.
When user connects to the database, depends on username, database provides group_name.
In this way program is automatically switched to OWNER or CLIENT group.