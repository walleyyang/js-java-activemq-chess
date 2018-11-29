![Screenshot](/javascript/src/images/screenshot.PNG)


This application was created as an example to learn ES6 AngularJS, ExpressJS, NodeJS, Webpack and Stomp with ActiveMQ. The chess move validation was created with Java.

## Development

## Install ActiveMQ 
Download, install and start ActiveMQ.

## Java
Using Eclipse, add the below list of external jars from the Maven repository to the project
activemq-all-5.15.6.jar (From ActiveMQ installation folder)
jackson-annotations-2.9.7.jar
jackson-core-2.9.7.jar
jackson-databind-2.9.7.jar
postgresql-42.2.5.jar

Run the project in Eclipse after adding the external jars.

## JavaScript
Install npm packages and then start
npm i
npm start


## Production Build
Make sure nodemon is not running, then run the below to create the dist folder.
npm run build

Requires NodeJS and PostgreSQL.

## Application
The default application is at localhost:3000
Go to the browser and create a game. Open another browser in private mode and go to localhost:3000. Add the user to join the game. The game will get deleted once a player wins.

Game validates:
The moves for all pieces.
Pawn promotion (only queen was used).
Game over once the king is taken out.
