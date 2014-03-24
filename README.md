Playover
=======
Playover is a low-fare flight search that turns those cheap fares with long layovers into something useful.
Instead of just seeing the inside of an airport, it will show you what you could do in each connecting city during your long layover.

Installing Playover
========
To run the app server, you need Java 1.7.10 or better, and Play Framework (www.playframework.com) v2.2.2.
Install both and ensure that play.bat or play.sh is on your path. Then do

play start

from this folder (the one with this readme your are reading right now) in it to start the app, which can be accessed at localhost:9000

Using Playover
=========
Points of note
* There is no aiport decoding, input airports must be IATA codes
* Input dates must be ISO standard. All prices are for return flights, where the return is departure date + 15 days
* Only results for connecting airport with content will be displayed. If there's more than one connection, we'll only consider the longest connection as the valid ones. Airport codes with content include LHR, CDG, ZRH, FCO, JFK, ATL, IAD, FRA and MUC
