# Music Streaming

This project is a simple Server-Client app for streaming data (music) using Java TCP sockets.

It was made very fast because we had little time to develop everything.

Subject: Fundamentos de Redes - Universidad de Granada (UGR)

Authors:

- Alberto Sola
- Joaquín Claverías

2016/2017

# Install / Compile

If you only want to stream data, you need to import the client/server objects (and java :P).

But if you want to play MP3 music, you need the library "MP3Seth".

Also you have to manually add songs under "songs" folder.

# Packages

- Server: manages the songs and waits for client petitions.
- Client: asks for the playlist and data.
- Common: shared data structures between server and client.
- UI: very basic command-line interface to play MP3.

# How it works

The server has a list of archivos (added manually, but it's easy to read a folder) and waits for requests.

 The client makes a request of some data and the server sends the data over TCP. "size,data"

 The server reads any file and sends it as bytes (so you can use the server/client classes as a base of your project).

 Our app (UI.main) uses the client to request the bytes to the server, and those bytes are decoded using MP3seth in order to play the song.

 Probably there are some bugs and the code is not optimized, but it was fun to make it in a very shor period of time!

# Usage

Compile and run: 1. Server 2. UI.main.