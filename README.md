# TelegramBotEmulator
Bot emultator for a Pizza Store using APIs to integrate with Telegram bot tools to emulate conversational interactions between a customer and a bot.

This Bot Emulator was developed in team to test the usage of a Telegram Bot API to make a conversation between a "customer" from Telegram and a robot.
The robot takes notes of the order interacting with the customar, trying to lead him to the end of the order.

At the end, the Bot asks for the ZIP Code and query an open API on the Internet to get the customer's address to deliver the purchase.

This coding effort was made for an MBA Course at FIAP College in Sao Paulo - Brazil.

Details of the implementation:
0. Structure
  This Java Application uses Maven for dependencies. Consider looking at the pom.xml for specific versions.
  It depends on the next APIs:
  
  1. java-telegram-bot-api-2.1.2.jar - The Telegram Bot API itself
  2. gson-2.7.jar                    - Used to parse JSON on consulting address via Zip Code on the internet
  3. okhttp-3.4.1.jar                - Dependency for Telegram Bot API
  4. okio-1.10.0.jar                 - Dependency for Telegram Bot API

1. Main Program

  Program starts at: GerenciadorBotFiapMain
  It depends on a config.properties file where it can find the key to connect the bot.
  Afterwards, it runs the parallel thread to listen to bot interactions.

2. Parallel Bot Thread

  The Parallel Thread is represented by the com.fiap.bot.threads.runnables.TratamentoDeMensagensRunnable program.
  It connects to the Bot and starts listening to the interactions, chosing and dealing with each one by passing them to the JarvisBot to decide what to do with it.

3. JarvisBot decider (tricky name!)
  
  This program is responsible to interate with customers considering each interaction, deciding in which part of the conversation the customer is and answers each interaction,
  separating one from each other by the customer Id. Very cool!

4. Administrative Console Application

  As far as the conversation goes on between the bot and the user of Telegram, the robot consolidate all the orders, 
  customers and purchases made and this simple administrative solution allows the view of the situation of sales, total orderes, with purchases was made and by who.
