# Bimbe di Turing
player for tablut client competition 

## Features 
 - Heuristic based on Manhattan distance and number of pawns on the board



## Run the Server without Eclipse

The easiest way is to utilize the ANT configuration script from console.
Go into the project folder (the folder with the `build.xml` file):
```
cd TablutCompetition/Tablut
```

Compile the project:

```
ant clean
ant compile
```
Run 
```
ant server
ant bimbewhite
ant bimbeblack
```
to create executable jar run 

```
ant bimbe-jar
```
 to run jar

```
java -jar bimbe.jar COLOR SERVER-ADDRESS TIMEOUT
```

### Authors 
 - [Longhi Matteo](https://github.com/carnivuth)
 - [Stefano Hu](https://github.com/hjcSteve)
 - [Caterina Leonelli](https://github.com/RootLeo00)
 - [Anna Vandi](https://github.com/AnnaVandi)
