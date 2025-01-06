author Nick Verhage

To run this program, you will need the following requirements:
  - network access
  - bash
  - Docker (made with 27.4.0)
  - ports 8080 and 5173 open
  - no current docker images named spring-temp-image or lf-frontend-image

To run the program, open a bash terminal and run the script runBoth.sh. (Figured it was easiest.)
Then, go to 'http://localhost:5173/' to see the frontend. The backend can be observed with docker 
desktop, or by re-running the spring-temp-image without the -d flag.

The weird naming of the modules is because I made one, and the other is yours. 

The supervisor dropdown menu has search functionality by firstname, lastname, and jurisdiction. Try
typing into it.

My javascript is entirely self-taught, so I apologise if the project structure is not industry standard.