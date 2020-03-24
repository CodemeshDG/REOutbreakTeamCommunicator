# RE Outbreak Team Communicator
Allows for essential and easy communication between teammembers playing Resident Evil Outbreak.

## What is it?

This is a multi-user app which allows players of the Resident Evil Outbreak video game to share online relevant information to their teammates. The game was released in 2003 and still is played online by a small community. Unlike modern games, there are no ways to truly communicate in game on an omnipresent team level. This app allows teammates to create a password-protected room where they can easily share their character's status and collaborate to complete level goals.

## What did I practice?

### Cloud Firestore & FirebaseUI / FirebaseAuth & AuthUI
The backbone of this app is the usage of Cloud Firestore to send and retrieve data from a database. I had to create methods which write to the database and retrieve/handle data. I needed to use associated listeners to anticipate and handle possible outcomes (success, failure, completion). Moreover, I utilized two libraries (FirebaseUI & AuthUI) to support login functions and populating RecyclerViews with database items.

### Firebase database rules
I needed to write code on the Firestore console to set up strict database read/write rules to secure user data. In the app-level code, this required using FirebaseAuth to authenticate users and using their uid in database-specific method calls, along with checking user-entered passwords against the database.

### WriteBatch
Regarding database writes, I learned how to use WriteBatch to make atomic writes (all or nothing). Not only is this more efficient, but it is a more stable way of doing certain operations, such as creating rooms in the database.

### Query
Regarding database reads, I learned how to set up a Query based upon certain parameters (such as limiting or ordering the results) and how to handle the object returned which contains the results.

### MultiDex
Because the Firebase libraries used in this app push the total methods beyond the dex file limit (65,536), I had to learn how to use MultiDex. Specifically in my circumstance--where I use a custom AppCompatActivity and cannot extend from MultiDexApplication--I needed to override attachBaseContext() and call the MultiDex's install method.

### Custom listeners
This app allowed me to experiment with custom listeners. These custom listeners are mostly involved with Firestore functionalities, and they have allowed me to minimize my code with reusable listeners for various situations.

### Code planning and revision
Beyond specific technologies, this app provided the opportunity to experience heavy code revision. I began building this app before I knew how I was going to handle a multi-user experience. Therefore, I had to carefully plan my revisions because a lot of methods and variables needed to be changed once I began using Firestore. Thankfully, Firestore allowed for revision which streamlined large portions of code.

As I implemented Firestore, I learned more about how to best use it, allowing for more revision opportunities. With WriteBatch, the code for creating a room went from a chain of write methods and listeners to a single write method and listener. By creating custom reusable listeners, the code was cut down even further.
