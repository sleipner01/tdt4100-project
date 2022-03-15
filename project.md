# Airline Manager

## Project description
This project is about creating a game, where a user can create and run his/her own airline, highly inspired by "Pocket Planes".
From a database of airports, the user select a starting airpotr and begin his/her journey on expanding the best airline in the world!
The user will begin with x amount of money, and is able to buy a plane to start with. 
The user can buy up to three airports to begin with.
For every passenger the flight will earn money. However, it will cost money to fly the plane, so it is important to balance this to make money.
The user can buy more planes when he/she has enough money for it. The user can buy more airports as well.
When the plane is flying, it will be unavailable for a certain amount of time.
The gates will periodically refresh the amount of travellers. The travellers who have boarded will stay in the aircraft.


Possible expansions:
- Make the airports have different distences from each other.
- Make the planes only fly a certain range.
- Make the airports contain different gate types, and for a plane to park, it must wait for another to leave if all the gates are full.


## Base classes
- Plane
  - Contsains information about an aircraft.

- Flight
  - Contains the aircraft and its passangers
  - Adds passangers
  - Sets the plane
  - Contains the revenue for the flight (Calculates)

- Airline
  - Contains the name of the airline
  - Contains the aircrafts
  - Contains the available airports
  - Contains the money

- Passanger
  - Depends on how much extensions I will add, but will be objects that pays x amount for a flight.

- Airport
  - Contains the passangers
  - Contains the list of airplanes at the airport

## File handling
I will store information on different airports and aircraft in seperate files, so the information can be loaded when the game starts.
The state of the game (Airline info, the location of the planes, every flight that hasn't departed) will be able to be saved, so that all information needed can be restored and the game to continue.


## Testing
- I will create tests to make sure the information about the aircrafts and airports are loaded correctly.
- I will test tuhat flights work withot a hitch
- I will test that airports can create new passangers
- I will test the the airports can add and remove planes that arrive / depart
- I will test that passangers can be added to flights, and removed from the airport


## Tasks
- Create AircraftInterface and figure out what I can use it for...
- Finish PlaneIterator. Will be used to iterate through an airline to find all of its planes.