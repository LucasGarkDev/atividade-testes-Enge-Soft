# MySQL database on docker 

To this example I use a MySQL database running on  [docker](https://www.docker.com/) container.

To build and run the container, go to database directory and run the following commands:

```
docker build -t java101-mysql .
docker run -d -p 3306:3306 --name mysql-db -e MYSQL_ROOT_PASSWORD=secret java101-mysql
```

If you want to connect container mysql console and make sure all it's fine, run this command: 
```
docker exec -it mysql-db mysql -p
```
And if you don't want to make all the building process, you can use my docker image published in dockerhub and just run follow command:

```
docker run -d -p 3306:3306 --name mysql-db -e MYSQL_ROOT_PASSWORD=secret devemg/java101-mysql
```
