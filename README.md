## Slick is a functional relational library in Scala which makes working with relational databases easier. We can interact with the database almost in the same way as we do with Scala collections.

## We will be using Slick with PostgreSQL and Hikari connection pool.

# Setup
- run `sudo docker-compose -f docker-compose.yaml up -d`
- run `sudo docker exec -it slick-postgres-demo_db_1 psql -U postgres`
- check if table is created on not by running `select * from movies."Movie";`
- Now compile and run *Main.scala* file
