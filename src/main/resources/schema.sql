CREATE TABLE IF NOT EXISTS film (
                                    id INT AUTO_INCREMENT PRIMARY KEY,
                                    title VARCHAR(255) NOT NULL,
    description TEXT,
    release_year INT,
    genre VARCHAR(255)
    );

CREATE TABLE IF NOT EXISTS actor (
                                     id INT AUTO_INCREMENT PRIMARY KEY,
                                     first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS film_actor (
                                          film_id INT,
                                          actor_id INT,
                                          FOREIGN KEY (film_id) REFERENCES film(id),
    FOREIGN KEY (actor_id) REFERENCES actor(id),
    PRIMARY KEY (film_id, actor_id)
    );