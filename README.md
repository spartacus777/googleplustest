# googleplustest
<h4>Test application</h4> 

App with login window using google plus account, and if the login is correct, it open a new window with 2 head tabs (framgents)
to switch between them (dummy content just to see the difference).
when the app is opened, it will automatically start a service.
every 10 seconds, the service will generate a random number and display it on the bottom part of the app.
if the app is not in focus, a notification will appear and when the user press it, the app window will open and 
show the last random number

<h4>Using:</h4>

Google plus api

Service, wich runs in background, even if app is closed
Glide

ButterKinfe

LeakCanary

Implemented Google plus login - logout flow
