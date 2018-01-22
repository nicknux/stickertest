# Android Sticker Development for Non-Developers
This app is meant to be a template that can be used by non-developers for their sticker packs.
It's been structured to make it easy to reference sticker assets with no Java coding required.

## Pre-requisites
- Install Android Studio from https://developer.android.com/studio/index.html

## Clone This Repo
1. Run the Terminal app on your Mac
2. Type `mkdir -p ~/projects/android`
3. Type `cd ~/projects/android`
4. Type `git clone git@github.com:nicknux/stickertest.git` OR `git clone https://github.com/nicknux/stickertest.git`
5. Type `cd stickertest`
6. Run Android Studio and open the stickertest project

## Firebase
1. Setup your Firebase account at https://firebase.google.com
2. Add a new project and name it "stickertest"
3. Add a new application (choose "Add Firebase to your Android app"), in this case use the name "com.nickdsantos.stickertest". If you are only using this project
as a template and have named your Android project differently, you can use the name in `app/build.gradle` next to the label `applicationId`
4. You'll be given the option to download the file `google-services.json`. Save it inside the `app` directory. It should be in the same location as the `src` directory.

## Uploading Your Stickers
1. While logged in to Firebase, click on the navigation item named **Storage***.
2. Click on the **Upload File** button and pick all the sticker files that you need to use.
3. When you're done uploading, click on every file that you have uploaded. Under each file's properties, is an option to see the **File Location**, take note of each file's
**Download URL**. You will use these URLs in the next section.

## Using Your Stickers
1. Open the file `app/res/raw/stickers.xml`.
2. Create an entry for every sticker file that you want to use, like this:
    ```xml
    <sticker
        name="<STICKER_NAME>"
        description="<STICKER_DESCRIPTION>"
        url="<URL_YOU_NOTED_IN_PREVIOUS_SECTION>"
        keywords="<KEYWORDS_FOR_THIS_STICKER"/>
    ```
    Example:
    ```xml
    <sticker
        name="Sticker1"
        description="Test Sticker 1"
        url="https://firebasestorage.googleapis.com/v0/b/stickertest-90f71.appspot.com/o/sticker01.png?alt=media&amp;token=1ec17147-ee61-42f7-9dfe-d0274020993a"
        keywords="test, sticker, nick"/>
    ```

## Setting Up The Emulator
1. In Android Studio, choose **Tools -> Android -> AVD Manager**
2. In AVD Manager, Click **Create Virtual Device...**
3. Select **Phone -> Nexus 5** (The one with an icon under **Play Store**
4. Choose Android 7 (Nougat)
5. AVD Manager will now list Nexus 5 as an available virtual device.
6. Run the Nexus 5 Virtual Device by clicking on the Play button
7. Open **Play Store** on the Emulator *(You need to login to your Google Account)*
8. Search for **Gboard** and update the app

## Running The App In The Emulator
1. Back in Android Studio, click **Run -> Run 'app'**
2. Choose Nexus 5 under Available Devices
3. The app will now run in the Emulator. Click on the button that says **Add Sticker Pack**
4. The sticker pack is now available in Messenger.

