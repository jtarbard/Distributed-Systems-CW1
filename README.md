How to start Service 1 (game-filter)

Download latest version of Maven from https://maven.apache.org/download.cgi

You can find installation instructions at https://maven.apache.org/install.html

Navigate to the service folder game-filter/
Execute the following command in command line:

mvn spring-boot:run

The service should now be up and running!

You can test the server by performing the following requests (using Postman/Axios etc):

http://localhost:8082/all/genres
http://localhost:8082/news/filter/genre=mmo
http://localhost:8082/giveaways/filter/genre=mmo

How to start Service 2 (reviews)

Download python 3
Install the following libraries via pip install

flask
flask_restful

navigate to the service folder
run the following command python api.py

the service should now run on localhost port 7777

Example Queries:

http://localhost:7777/steam/portal_2
http://localhost:7777/metacritic/Pokemon_Y
http://localhost:7777/client/doom

Dataset sources:

https://www.kaggle.com/trolukovich/steam-games-complete-dataset
https://www.kaggle.com/deepcontractor/top-video-games-19952021-metacritic

How to test the Web User Interface:

Run service 1
Run service 2

Open index.html in your browser

Enjoy :)