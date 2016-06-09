---
layout: post
title:  "Project Redefined"
date:   2016-06-07 2:46:56
first-name: Ademola
last-name:  Kazeem
---
Written and Posted by: {{ page.first-name }} {{ page.last-name }} 

## The Initial Project Idea.
The initial idea was to develop a project is to develop an app named RestroFinder in which users can snap names of restaurants and get all the required information about the restaurants on the fly. The user is relieved of the stress of having to type the name of the restaurants in order to get information. Our app will help users get details about any restaurant they are interested in. 
Central to this development was an OCR image conversion to text and API calls are made to be able to display information about the restaurants.
Elaborately, the image of the restaurant name would be converted into text. Based on that text we will call some API which in turn will fetch us the details about that restaurant. Details such as the restaurant location, opening time, food menu, restaurant rating and reviews and the cuisines served etc. will be displayed. An alternative option to clicking snap is user can speak out the name of the restaurant. Here using voice recognition an individual’s voice would be converted into text and the same process would be followed. The third option that we are providing is - any user can directly type in the restaurant name in the text field provided and similarly he will get all the details. This option is provided in case if the user’s camera is not working or if the voice is unrecognized then he can directly type in the restaurant name and get the desired details.

## The Initial Project Redefined
After careful evaluation of this idea, we discovered that snapping of pictures using OCR image conversion might not solve lots of problems. We figured out user would rather type or just speak to the app to be able search for information about restaurants. We were not convinced that users would have reasons for snapping rather than typing or speak to the app.
Every other features remain intact but, we are removing the OCR image conversion, because the use case does not seem to appeal to the users of the app.

## The Users

The application will be used by anyone that want to or like to eat outside, the party goers, lovers who want to treat the love of their lives, visitors to a particular vicinity, the lovers of pubs and nightlives, etc.


* User talks to the app and get information about the restaurants on the fly
* User types in the app and get information about the restaurants on the fly
* The information maybe cuisines, opening and closing hours, prior closure, reviews etc.
* Based on user’s restaurants visits (location), other restaurants with similar cuisines etc. within some miles are recommended regularly for the user.


## The Problems / Use Cases
There are lingering problems that are facing users of this application as expressed above, some of the problems include:

1.	A user is new in an area and he/she is looking for somewhere around him/her to eat at cheaper price.
2.	A user is new in an area and he/she is looking for somewhere to each at cheaper price.
3.	A user is oblivion of classy restaurants to give his/her spouses the best treat ever.
4.	A user wants to be reminded of the closing time of his/her favorite restaurants so he/she can quickly get to the restaurant before closing time.
5.	A user wants to know the prior closure notice by the country’s authority.
6.	A user is driving and cannot type on the screen, he simply opens the app and voice over to specify what he want to eat and list of restaurants, pubs etc. will be displayed for him/her to see.


## Literature Review

We are reviewing three main app namely: Zomato, Yelp and TripAdvisor

## Zomato: 
Out of these apps, Zomato is in our opinion is the best restaurant app we have reviewed. Some of the problems above have been solved by Zomato.

Any individual regardless of age limit can use this app. Our app will be handy for all the food lovers who are fond of trying new cuisines and restaurants or someone who is a frequent traveller.

### Some Features:

1.	Recommended trending restaurants
2.	Shows nearby restaurants 
3.	Breakfast, Lunch, Diner, Cheap eats, Cuisines
4.	Privatize bars, Date spots, Dessert places

##  Yelp: 

Allows users to search for restaurants, bars, coffee and Tea shops, Delivery, Reservations.

### Some Features:

1.	Instead of recommending the trending restaurants, yelp shows trending keywords like Lunch, happy hour, etc.
2.	Activity involves nearby public places (restaurants, pubs, etc.) but not displayed on the map like Zomato
3.	Recommendations for different time foods, like recommendation for afternoon snack, Morning recommendation of cuisines, Evening recommendations.

### TripAdvisor: 

TripAdvisor is more than just restaurant, it allows users to book hotels, book flight, do several things more than just restaurants.

### Some Features:

1.	Hotel Search: This searches hotels in the vicinity, the distance, the reviews, etc. from the current location.
2.	Restaurant Search: The app searches for restaurants within the vicinity of the users. Users can see the reviews of the restaurants 
3.	Flight Search: Users are allowed to search for flights online and make bookings.
4.	Things Search: You can search for places near you apart from restaurants etc.
5.	Holiday Rental Search: Users can search holiday rentals.

##  ChewIn:

For different reasons, our application had been given different names, from Snapr, to Restrofinder and now to ChewIn.
We named the application Snapr because the snapping was the Centre of the development, when we streamlined to restaurants, Restrofinder came to mind. The group has finally chosen ChewIn to denote that the app is centered around the users’ location and restaurants. 
The app contains some of the features of the above and with some new features that makes it better.

### Features

1.	Location-based Search system
2.	Users are recommended nearby restaurants. 
3.	Recommend trending restaurants in the user’s area
4.	User talks to the app and get information about the restaurants on the fly
5.	User types in the app and get information about the restaurants on the fly
6.	Based on user’s restaurants visits (location), other restaurants with similar cuisines etc. within some miles are recommended regularly for the user.
7.	The information maybe cuisines, opening and closing hours, prior closure, reviews etc.

### New Features:

1.	All other apps reviewed do show you based on food you have searched, mostly based on cuisines, but ChewIn allows you to search based on name of a food not only cuisines.
2.	Users’ preferred cuisines are specified once the user logged in and the nearest restaurants with the cuisines are recommended.
3.	Users are allowed to specify their preferred restaurants and notifications are sent to the users like one hour to the closing time of the restaurants to remind the user in case he/she wants to visit the restaurant before closing.
4.	The users’ restaurants recommendation is based on the age, of the person, smoking and drinking habit etc. 

## Conclusion

Apart from the snapping functionality that was removed from the application, every other functionality mentioned in the last post including the data source used, technologies used etc., are still valid. These new features are what makes new app better than the reviewed apps.
In the next post, we will explore more on the project plan and the architecture of the system.
