---
layout: post
title:  "Integration of Systems and Wrapping Up"
date:   2016-08-01 2:01:56
first-name: Ademola
last-name:  Kazeem
---
Written by and Posted by: {{ page.first-name }}  {{ page.last-name }}

## The Overview.

In the last two demo by team, we were able to show the minimum viable product (MVP) of our application which was to skip login and show all the nearest restaurants. In the last show-and-tell and demo, we showed that we have developed recommender systems, introductory login page using Google and Facebook login, but we had not integrated them. Now, we have been able to incrementally integrate Splash Screen, Welcome Activity that shows the login for the user, and Skip login that allows the users to quickly get restaurants close to them, Python Daemon Script, Recommender systems, Menu-based search, Checkin, bookmark, Filter and opportunity for users to edit their profiles.
 

## Server Configurations.
Some part of the application is going to run on the server, so there is need for server configurations and preparations. 
   
### Java JDK Installation
So, the first thing we did was to install Java on the server, since our application is developed with Java, Python and some other Java and Python frameworks, we installed Java JDK on the server to enable applications running on JDK to run smoothly

### PostgreSQL
The application runs on PostgreSQL, so we installed the database on the server, using the following commands:

![Installing postgreSQL](/Snapr-Team/images/sudo-apt-get.png)

After the installation, a user account named postgres was created as and it is associated with the default postgres role.

![Changing postgreSQL](/Snapr-Team/images/sudo-postgres.png)

To create PostgreSQL objects, command lines needs to be used in order to create postgres tables. However, there are myriads of Graphical User Interfaces available for installation that would allow users to create table interactively. The GUI of choice is teampostgresql of http://www.teampostgresql.com/ because it allows us to create tables and other objects using browsers.

![Installing postgreSQL](/Snapr-Team/images/teampostgresql.png)

## Python Daemon Script.
Zomato provided APIs for developers to use in order not to reinvent the wheel. On regular basis, the company provides updates on all the data provided through the API. So, in order for us to have the latest data available on our server, a python script was written that goes back to the Zomato API every week to fetch the most recent data and add it to our data, while updating any changes from the last fetch. 

![Command line](/Snapr-Team/images/whole.png)

## Challenges with Running the Script.

After developing the python script, we faced the huddle of making it run on the server as it worked perfectly on the laptop and was able to connect to the database on the laptop. Running the script on the server was a different ball game entirely. The reason was because Ubuntu 14.04.1 has many python versions installed.

![Command line](/Snapr-Team/images/challenges1.png)

As shown in the diagram above, there is python2.7, python3.4 and python 3 installed on the server which the operating system itself uses for its daily activities, so once you do pip install for all the modules in the application. For instance, we made use of psycopg2 module for database connection, pandas module for CSV and requests module. 

The solution was to install Virtualenv, Virtualenv is package that allow developers to install virtual python. This enable the developer to make use of their custom-installed python to run their scripts.

![Command line](/Snapr-Team/images/challenges2.png)

## Challenges with Scheduling.

After crossing the huddle of running the script, the next of action was to schedule the script to run at a particular interval. There are a view options available for us. We could make use of the Crontab by scheduling the Cron jobs, the use of Celery and the use Cron and Celery. 
Eventually, we settled for the use of Crontab because it comes with Operating system, and limited work had to be done in order to get it done. It is also more reliable than some frameworks like celery that we had to learn well before implementation considering the limited time we have at our disposal to wrap the project up.

![Command line](/Snapr-Team/images/cron.png)

At the moment, we set the Cron Job to run every day at 18:45, which would be changed soon once we decide how many times we want to update our data within a month.

## Recommender System and Menu-based Search.

As mentioned in the previous posts, these two important features have been integrated into the application. The recommender systems get information from the database about the user. Once the user logs in for the first time using Facebook or Google Plus, we get the users’ data, and the user can also edit the profile and save it into the database for future use, the information gotten from either Facebook or Google Plus or the updated edited profile by the user among other preferences selected by the user is used recommend the best restaurants for the user.

## Splash Screen, Login Page and the Navigation Drawer Integration.

In the last Demo, we were unable to show all the splash screen, login page and the navigation drawer consisting of the user profile pictures gotten from Facebook or Googleplus because it was located and developed by another member of the team and was not integrated yet. Now we have them seamlessly integrated into android app developed.

![Screenshot](/Snapr-Team/images/Splash.png)

![Screenshot](/Snapr-Team/images/Login.png)

![Screenshot](/Snapr-Team/images/LoggedInFB.png)

![Screenshot](/Snapr-Team/images/LogginFB1.png)

![Screenshot](/Snapr-Team/images/LogginFB2.png)

![Screenshot](/Snapr-Team/images/LoggedInGP.png)

## Next on the Agenda.

We are rolling out evaluation to users on how they can rate our applications, we are providing application rating interface with the app to enable the rating faster and less stress. Also, we are wrapping up on some trivial functionalities like the Dialog boxes about sign out, perfecting our recommendation and menu-search, a better UI design that will be more appealing to the user. If we are able to finish all this on time, we would implement the Voice recognition on the menu-based search.











