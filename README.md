### ¿Why this project?

This project has been made just for fun and in order to practice with third parties with springboot. This is not even 100% functional for final user, but i am sure that it reached the purpose for me. Learn&fun while coding, thats all. Well, and of course, if it is useful for anyone who want to know how Telegram bots works with spring, for sure they can find some value information here.

### ¿What is it about?

It is a little and simple Springboot app that serves as backend for a Telegram bot. This Telegram bot can be used to translate text from english to spanish.

To use the bot you just have to:

- Have installed Telegram
- Search the conversation translator_telegram_bot
- /start (this app must be running, obviously)
- Write text in english, and it will be sent to the app. This text will be translated from Google Translator.
- Write translated text to the bot conversation.

### Telegram

> "Telegram is a messaging app with a focus on speed and security, it’s super-fast, simple and free. You can use Telegram on all your devices at the same time — your messages sync seamlessly across any number of your phones, tablets or computers. Telegram has over 700 million monthly active users and is one of the 10 most downloaded apps in the world."

Go to official [FAQ](https://telegram.org/faq#q-what-is-telegram-what-do-i-do-here) for more info.


### Telegram BOT

Telegram brings you the opportunity to create a BOT that you can use for multiple purposes. You just need to create it, create and configure an app to control it.

To create a bot, open your telegram app through your smartphone, web or desktop version. Just search for a contact called BotFather, open a chat and follow instructions. Cant be easier.
![botfather_past25](https://user-images.githubusercontent.com/64311948/182045351-1b4b8ea2-9bfd-472b-9cf9-90e1c74c4c33.png)

In this case we used a springboot application that has a class that extends from TelegramLongPollingBot. For having this library, just add to the pom:

`
<dependency>
    <groupId>org.telegram</groupId>
    <artifactId>telegrambots-spring-boot-starter</artifactId>
    <version>6.1.0</version>
</dependency>
`

Once you extend from TelegramLongPollingBot, you need to override three methods:

- onUpdateReceived -> It is the entry points of the info coming from the bot. What the user writes in the telegram chat, arrives here. Just do with it, what you want to do for the user.
- getBotUsername -> Set here the name of the bot. That ones that ends with _bot.
- getBotToken -> Set here the access token that botfather gave you when you created the bot. Remember to be careful with this sensible information. Don't commit to git :bangbang: 

- Read the [FAQ](https://telegram.org/faq#q-how-do-i-create-a-bot) for more info.


