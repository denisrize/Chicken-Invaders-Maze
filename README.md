# Chicken Invaders Game 

![header](https://user-images.githubusercontent.com/55393990/192237491-e5a21492-8313-4700-ba21-c4ff4c4ed5a1.jpg)

### General
This Maze Game was build as a part of Advanced Topic In Programming Course with my Project partner. The primary goal of this project is to animate maze generation and solving algorithm while learning and implementing well known design patterns such as Observer, Bridge, Singleton, Strategy etc.

### Servers
In addition we bulid two servers, Each one of them have different Purpose. The first server supports on genertaing the maze by DFS algorithm. The second server supports on solving the maze with algorithm chosen by user ( default Best First Search). For supporting in parallel programming so multiple clients can contact with the serevers, we also used ThreadPool. User can change the pool size and the solving algorithm simply by the configuraion file. Also we created a compression algorithm for the maze to make communication between client-Server much faster!  

### GUI

The GUI in this project was build with JavaFX as a MVVM (Model-view-viewmodel) architecture. Model is dealing with the business logic, the view layer with all the visual design GUI and the ViewModel is the connection part between them. This part of the project was more for our enrichment and fun, so we had to do a lot of self learning in a very limited time ( you can say we were thrown into the Threadpool :sweat_smile:).

### Features

Playing this game you got the following features:
- Generate a maze by row and column size as you wish.
- Save and load mazes from the disk, if you want to challenge your friends or just to save the it for another time.
- Solve that maze and be presented with the solution from the player standing position.
- Change cofiguration of the thread number in the thread-pool and search algorithm for solving the maze.
- Each time you finish the maze you get a score based on the number of steps you done ( Gold,Silver and Bronze trophy).

### Some Example images

User asked for help:

![HelpMeSolve](https://user-images.githubusercontent.com/55393990/192246381-867d7ce2-50a5-43c4-9d51-9780aa9d9d06.png)

User points for solving that maze:

![MazeSolvedHelp](https://user-images.githubusercontent.com/55393990/192246608-7517aba3-4514-45ab-b6cd-e9d049278f07.png)

About:

![AboutSection](https://user-images.githubusercontent.com/55393990/192246745-658ffb08-d8a3-4c58-aa9a-0a2a9e2819d0.png)

Good luck dear invader!

