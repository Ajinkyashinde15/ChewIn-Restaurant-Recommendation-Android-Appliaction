---
layout: post
title:  "Project Overview"
date:   2016-06-13 2:46:56
first-name: Kushal Mishra
last-name:  Ademola Kazeem
---
Written by: {{ page.first-name }} 

Posted by: {{ page.last-name }} 

### New Features added.
   From our last update we have added two new features in our app ChewIn.
This two features will help our users to search any restaurant in future and will reduce their work of remembering anything is particular. This two features are:

* Bookmark  and Bookmark History
* CheckIn and CheckIn History

On third page of our app ChewIn, at the bottom of the screen user will have three options as Nearby, Bookmark and CheckIn. Nearby feature was explained in our last blog post but here I would like to present a use case for this feature While the bookmark and checkIn feature on this page would be history of user featuring all the restaurants they have bookmarked or CheckedIn in past.

On fourth page of our app, we again have bookmark and CheckIn feature but this time, user can actually bookmark certain restaurant or CheckIn in that particular restaurant.

### Use Cases for Nearby option
After user fills in the form that we ask them to fill, we recommend restaurants to them as per their choice that they made while filling up the form. But there might be case that it’s not what our users would be looking for, they would be in need for something more, they want to know all restaurants that is around them despite of what they want in particular as it’s always good to know all option around you rather than just sticking to some in particular. Our nearby option would do this in particular. It will suggest all the restaurants that is around their current location without applying any recommendation or filtration. It will give them more options in random and all available restaurants around them.

### Use Cases for Bookmark and CheckIn

We got this idea from other apps which are not for restaurant services but for other daily use like Dublin Bus app, here we can bookmark certain bus stops thus we need not require to remember them every time we are travelling. Even while browsing internet if we feel that some of the website is very useful and would be helpful also in future we normally bookmark that website. Same would be the case here, if a user finds a good restaurant and he can wishes to visit there again, he can bookmark that restaurant in particular and it would be saved in a list from where it would be easy for him to find it later.
Our other new feature would be CheckIn which would be similar in as bookmark but here user can show publically that he has CheckIn that restaurant.
Introducing this two features is helping our application of ChewIn in many ways, like looking at the choices user has made to bookmark or CheckIn in a restaurant, it would help us to know user’s choice of preference and thus we can recommend better restaurant to user which would be more similar to choices he makes normally.

Other use of this feature that we have though is for the long term use as we can generate an annual list of restaurants that were more bookmarked or Checked In by people thus showing the popularity of that particular restaurant. 


### Additional Features

First we were only planning to suggest all nearby restaurants in the surrounding of user based on their location, but now with this feature we are adding a feature of get direction. When a user selects a particular restaurant for further details, we again show him a map but this time map will suggest our users with a direction by which he can reach that restaurant even if they are new to that area.


### Use case
Suppose a person has visited Dublin for the first time and he is looking for some restaurant from our app and he would like to visit that place but how would he know the direction? One way is to copy that restaurants name and then google that place to find the direction from his current location but this would be too much work for him and we want our user just to enjoy their visit and rest to be managed by us. So we are now introduction this Get direction feature in our app ChewIn to ease our user’s work.

### Task Completed

Till now we have finished some of our very basic task like Getting logged in our app ChewIn via Facebook, Getting current location of user, GUI of first two activity pages of our app.


### Task In-Progress

Many of our task is in pipeline and it’s like we are about to take off for a start. We are developing GUI of rest of the activity pages, collecting data and creating a database for our recommenders System. We are doing this by hitting API’s using Java Web Services and we get all the data in JSON file which we can use as per our convenience. 
One of the reasons for SCRUM approach is to allow for change, we might change some technologies and some requirements might be added as the project progresses.


### What’s next?

Our first priority for next week would be to get almost ready with the GUI of our app. Start with recommenders System part once all the data is collected. 