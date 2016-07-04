---
layout: post
title:  "The Milestone"
date:   2016-07-05 15:00:56
first-name: Ajinkya Sinde
last-name:  Ademola Kazeem
---
Written by: {{ page.first-name }} 

Posted by: {{ page.last-name }} 

### The Milestone.
Post the launch of MVP we have introduced a new feature which will enable users to simplify their search. We have introduced a feature in which user can choose the radius, and based on his choice we will display the list of restaurants that fall within that radius. Additionally, we have also introduced the feature of filtration, in which user can mention the restaurant count in the text box provided and result provided would be based on review and cuisine type filtration. This feature will fetch users with a list of top restaurant within the specified miles. Moreover, the navigation path will walk him to the destination. Hence by introducing these features, user can be more specific and less confused with respect to making a choice of a place to dine.
For example, if user is standing in Ballsbridge and slides the radius range to 2Km with a count of 5, then ChewIn will display him the top 5 restaurants within a range of 2Km. The selection of top 5 restaurants would be based on the recommender system. If he is a first time user then, we will recommend him top 5 restaurants based on distance, meaning starting from the restaurant nearest to him to the restaurant that is far off. But, if the user profile is already created, then the top 5 choices would be based on his fondness of any particular cuisine (customer specification).

Usecase diagram
![Use Case](/Snapr-Team/images/use-case.png)

Apart from providing details about any specific restaurant, out app will recommend an individual few other restaurants based on his current location. Initially the recommendations will be based on the users last visited history. For example if the user had last visited any Chinese restaurant then our app will provide him few suggestions of other Chinese restaurant in the location where he is at the moment. Similarly, if the user wants the details of any specific restaurant and he snaps a picture of it; then based on his current location we will recommend him few other restaurants in the nearby vicinity. A list of all restaurants within specific range will be suggested to him.

### Tasks Completed

The newly added features discussed above are incorporated in the MVP. So, at the moment we are able to spot user’s current location and provide him the restaurants in the nearby area. Moreover, user can now select the radius using the slider and enter the number of restaurants he wants to see. As per the count mentioned by the user, a list of restaurants will be displayed. Currently we are able to identify top 5 restaurants based on distance i.e. the ones nearest to the user will be displayed. Also we have developed the navigation path which shows the way from users current location to the destination restaurant where he wants to dine.
We have also completed the Facebook and the Gmail login options. Users can now login with either of their accounts and then a form will be displayed to him which will take into consideration his preference of cuisine.

### Task In-Progress

Currently we are still in the process of gathering data from different API’s (restaurant information and menu) and removing redundant data. We also started to look for data from different sites (like Deliveroo.ie). Once the data collection is done we will have good amount of data to ensure that our recommender system is more precise. Simultaneously, we are working on the UI to make it more attractive and user friendly.
Most of the android work is done and now we are focusing only on recommender system. Our data collection task is almost over and we will focus more on suggesting restaurants based on cuisines, reviews and rating. 

### What’s next?
Most of the android work is done and now we are focusing only on recommender system. Our data collection task is almost over and we will focus more on suggesting restaurants based on cuisines, reviews and rating. 

### Challenges

*	Collecting data from different API’s and merging it into a single database without any redundancies. This is quite a time consuming process as we are removing all the stop words (a, the, etc.) to avoid data redundancy.
*	We are searching based on menu but many API’s don’t have the menu option. So collecting menu from different API’s is a humongous task.
*	Zomato developer permits to make a maximum of 1000 calls to the API per day to access the licensed content. For now we are using different developer accounts and API-keys to make more than 1000 calls per day. 
*	Getting review of each restaurant for filtration as each API (Zomato, Yelp, etc) has different review and rating for same restaurant.

