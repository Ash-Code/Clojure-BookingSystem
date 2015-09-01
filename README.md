# booking
A light web application that maintains a record of bookings for rooms. Booking days span from the current day to 7 more. 
Allows meeting and room description, adding meetings, adding rooms, and prevents empty data entry.

FIXME

## Prerequisites

You will need [Leiningen][1] 1.7.0 or above installed.

[1]: https://github.com/technomancy/leiningen

## Running
You need to create the databases first
Run the following commands in REPL
    `(create-meetings-table)`
    `(create-rooms-table)`
    
To start a web server for the application, run:

    lein ring server

## License

Copyright © 2015 FIXME
