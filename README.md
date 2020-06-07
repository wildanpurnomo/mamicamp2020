# MamiCamp 2020 Android - My Version
Version where I follow my ninja way.

Demo Video: https://youtu.be/uA93P8mFf2g  
APK: https://drive.google.com/file/d/1Tv46iwyuhtuUupebN0h_KmalzfCJT1MJ/view?usp=sharing

Features:
* Authentication
Using Firebase Authentication (email - password auth).
Here's list of available account:
1. email: freddy@user.com, password: freddy
2. email: chica@user.com, password: toychica
3. email: bonnie@user.com, password: bonnie
4. email: foxy@user.com, password: toyfoxy  
If you want to make account for yourself, please use dummy email.

* Practice Mode  
Mode where user is introduced to the game. Upon first installation, there will be ShowCaseView displayed.

* Single Player Mode  
Mode where user attempts to get as many game points as possible. The gameplay is still the same, however the button moves upon the click.

* Leaderboard  
App records user's maximum point from Single Player Mode and rank it in the leaderboard along with other users. Leaderboard displays top 10 points. Cloud Firestore is used.

* Multiplayer Mode  
I have an idea to simulate multiplayer version of this game using WebSocket backend service. This is not done yet. I've developed the backend service (code stored in branch "multiplayer-service"). The problem is I still not be able to connect with local server using OKHTTP's WebSocket, however I can connect to the remote server. I'll continue working on this.

I'm using MVVM + Repository pattern.

Thank you. It was incredible course, it gave me a a lot of knowledge in Android Development. If it is possible, please kindly give feedback on this code. Thank you very much.
