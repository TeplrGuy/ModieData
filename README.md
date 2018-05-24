# MovieData

(Note Oriinal README file was deleted by accident when I marged just now :( )

Basic Program that calls themoviedb api using movie titles and returns information about possible movie matches.


# Database DLL
```sql
CREATE TABLE director (
    id          INTEGER PRIMARY KEY ASC ON CONFLICT FAIL AUTOINCREMENT
                        UNIQUE,
    director_id INTEGER NOT NULL
                        UNIQUE,
    name                NOT NULL,
    link        VARCHAR NOT NULL
);
CREATE TABLE movie (
    id             INTEGER PRIMARY KEY ASC ON CONFLICT REPLACE AUTOINCREMENT
                           UNIQUE,
    title          VARCHAR NOT NULL,
    description    VARCHAR,
    original_title VARCHAR,
    directors      VARCHAR NOT NULL,
    movie_id       INTEGER
);
```
You can create your own db for testing this program by using the the DLL above. 
You also have the option to use the db I used for testing, which is included in this repo.


# Usage
Took the liberty of including all external libraries used
- Located: /Lib in this repo

External Libraries
- json-20180130.jar: used to parse JSON response from themoviedb
- sqlite-jdbc-3.21.0.jar: used to interact w/ our sqlite db

When you first run the program you will be given these options:

Kindly follow the options below!
1. Search For a Movie
2. Display Options
3. To Quit

- 1 Allows users to input a specific movie title and inserts or updates the database.
  - Tables affected: movie and director
- 2 Allows user to re-display user input options
- 3 Allows user to terminate procress
