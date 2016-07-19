# like counter

This is a simple sample app that shows the use of figwheel for cljs client and a clj server.

This sample servers up one page that has two links, one that sends a POST to the server to increment the like counter.  The other link increments the like counter using a websocket.

## Configuration

Before you can run, you will need to configure nginx to direct webrequests to the `resources/public/` folder for your director structure.  You also need to allow access to the backend port from your fronend, I have an nginx configuration file under `config/nginx` that can be modified to point to your installation location and then copied to /etc/nginx/conf.d.  Onces copied over nginx needs to be restarted.

## Running

In order to run this you will need to start both the figwheel and the server.

In one terminal:

```
lein run
```

In a second terminal:

```
lein figwheel
```

Open a web browser and open `localhost:3599` which should bring up the page.  Clicking on either link should increment the counter.  And the `lein figwheel` terminal should be in a REPL now.
