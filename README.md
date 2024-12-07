> [!CAUTION]
> This project was aborted, in favor of another project with similar nature, with arduino.

> [!CAUTION]
> This project was aborted, in favor of another project with similar nature, with arduino.

> [!CAUTION]
> This project was aborted, in favor of another project with similar nature, with arduino.

> [!CAUTION]
> This project was aborted, in favor of another project with similar nature, with arduino.

> [!CAUTION]
> This project was aborted, in favor of another project with similar nature, with arduino.

> [!CAUTION]
> This project was aborted, in favor of another project with similar nature, with arduino.

> [!CAUTION]
> This project was aborted, in favor of another project with similar nature, with arduino.

> [!CAUTION]
> This project was aborted, in favor of another project with similar nature, with arduino.

> [!CAUTION]
> This project was aborted, in favor of another project with similar nature, with arduino.

> [!CAUTION]
> This project was aborted, in favor of another project with similar nature, with arduino.

> [!WARNING]
> Documentation is only partly completed, there might be changes to existing parts and surely new things will be added, especially those marked with **TODO**

**THE SERVER SHOULD NOT CHECK FOR THE INTEGRITY OF ITS OWN CONFIG FILES, SINCE THE SERVER IS NEVER AT FAULT**
**THE PRIORITY FOR TODOS OF DOCUMENTATION IS 10

# Table of TODOs 

|TODO DESCRIPTION|TODO PRIORITY (1 least, 10 most)|
|:--|:--|
|GENERAL TODOS|GENERAL TODOS|
|write tests and start understanding how junit works|10|
|Spell check and final revision|1|
|Update gitignore to make it so that the bin folder is ignored|10|
|use junit|10|
|---|---|
|TODOs of implementation|TODOs of implementation|
|---|---|
|**TODO FIGURE OUT HOW TO USE MAVEN TO BUILD THE JAR FILE OF THE INSTALLER CLASS**|9|
|**TODO change Source folder's name from "servertelefono" to "serverphone"**|1|
|**TODO MAYBE ADD A SECTION TO TALK ABOUT JAVADOC**|10|
|Setting up the server|Setting up the server|
|---|---|
|**TODO Understand termux and how to optimize the device to use it.**|2|
|**TODO install git and deploy the server code to termux and run the jar file and test it**|2|
|Document how the installer works|10|
|Console|Console|
|---|---|
|**TODO: IMPLEMENT ON THE CONSOLE a way to generate a new certificate**|6|
|**TODO ADD A WAY FOR THE SERVER AND CLIENT TO PROCESS AND SEND A MESSAGE.**|6|
|**TODO ADD A WAY TO NAVIGATE THROUGH FILES WITH A CLIENT**|6|
|**TODO ADD, FOR THE ADMIN, TO SEE HOW MUCH MEMORY IS ALLOCATED TO A USER**|5|
|**TODO IMPLEMENT ALL SERVER CONSOLE FUNCTIONS**|7|
|Files and storage|Files and storage|
|---|---|
|**TODO If the storage to accept the received data becomes insufficient during the receiving operation, send a message (TODO: IMPLEMENT THIS) to say that the storage is not enough, then start discarding all characters until you reach MESSAGE_END_SEPARATOR, that is a new message**|3|
|**TODO ALSO MEMORY COULD BE SUFFICIENT AT THE START OF THE REQUEST BUT COULD NOT BECOME SUFFICIENT AS THE DATA IS SENT, SO YOU HAVE TO SEND A NOT_SUFFICIENT_STORAGE MESSAGE IN THAT SITUATION, TOO, AND ELIMINATE THE FILES THAT WERE GENERATING**|3|
|**TODO TO KEEP THE ACTIVE MAP OF CLIENTS AND THEIR KEYS, USE A THREAD SAFE IMPLEMENTATION OF MAP**|9|
|**CONSIDER THAT YOU CANNOT CALCULATE HOW MUCH STORAGE IS BEING USED BY SERVER BY HOW MUCH STORAGE IS ALLOCATED, BECAUSE THERE ARE SITUATIONS WHERE LESS IS ALLOCATED THAN WHAT IS OCCUPIED**|10 (it is a problem of documentation, you have to understand how to deal with this situation, then the implementation will have a low todo score)|
|**TODO Document that more storage can be allocated than what is on disk, but that if it becomes insufficient then the writing operation stops and the data that was being written will begin to be eliminated**|10|
|User management|User management|
|---|---|
|**TODO REFACTOR AUTHORIZATIONLEVEL WITH A BOOLEAN ISADMIN**|6 Along with implementation of client|
|**TODO IF AT ANY TIME A USER DISCONNECTS ABRUPTLY IT HAS TO BE REMOVED FROM THE LIST OF ACTIVE CONNECTIONS**|5|
|Protocol|Protocol|
|---|---|
|make it so that the header is of a fixed length|10|
|**TODO IMPLEMENTATION AND DOCUMENTATIOM TO HAVE A REGEX WHERE ALL FIELDS OF THE HEADER ARE SEPARATED BY \|**|10|
|**TODO CREATE ERROR_CODE HEADER TITLE, TO SPECIFY ERRORS THAT THE SERVER INITIATES**|when it becomes useful, for now i put it at 8|
**TODO IMPLEMENT CERTIFICATES**|3|
|TODOs of documentation and studying anmd understanding project's nature|TODOs of documentation and studying anmd understanding project's nature|
|---|---|
|Concurrecy|Concurrecy|
|---|---|
|**TODO UNDERSTAND FILE STREAMS AND FILE LOCK**|7|
|**TODO UNDERSTAND HOW SOCKET STREAMS WORK**|8|
|Protocol|Protocol|
|---|---|
|**TODO ADD DOCUMENTATION ON HOW TO BUILD PROJECT**|10|
|**TODO PUT MORE INFORMATION ABOUT THE TYPE OF PUBLIC AND PRIVATE KEY**|10|
|**TODO ADD MESSAGE HEADER TO REMOTELY ACCESS THE CONSOLE**|10|
|**FIGURE OUT WHAT HAPPENS IF THE CLIENT SENDS A SERIES OF MESSAGES WITH THE SAME UUID IN SEQUENCE, FOR EXAMPLE TO UPLOAD VERY LARGE FILES. SOLUTION IF THE SERVER SEES THAT A MESSAGE HAS A REPEATED UUID, IT SENDS AN ERROR CODE TO THE CLIENT. IF THE CLIENT WAS IN GOOD FAITH, IT WILL USE IT TO KNOW THAT THIS HAPPENED ACCIDENTALY, AN INCREDIBLE RARE OCCURRENCE, AND WILL RE-SEND THE MESSAGE WITH A NEW CODE. IF THE USER WAS IN BAD FAITH, THEN THIS PROTECTS THE SERVER**|0 (do not implement this)|
|Files and storage|Files and storage|
|---|---|
|**TODO WHAT HAPPENS IF AN ADMINISTRATOR TRIES TO WRITE ON A USER's FOLDER, AND EXCEEDS THE DATA LIMIT IMPOSED? WHAT HAPPENS IF IT DOESN't SURPASS THE DATA LIMIT IMPOSED, BUT THE DATA LIMIT THE STORAGE UNIT THE SERVER IS ON?**|10|

# ServerPhone
_Believe it or not, this is the third time i delete this repo because of configurations issues, which took me a day to sort out.<br>
I finally managed to publish my work here, i will keep you posted here._<br>
<br>
Special thanks go to classical, jazz, phonk, and rock music for keeping me company during the creation of this little project.<br>
<br>
This project supports full JavaDoc documentation, so feel free to consult it.<br>
<br>
The documention in this file is comprehensive and aims to have enough detail, so that one could run with it and make their own implementation.<br>
If something is unclear, please do feel free to contribute in any way you see fit.

## Building the project
The server is installed in the folder where the executable is located. To start up the server from another location please use a shortcut. Here it will keep information about its users, and the data.

## Purpose
This client/server application is a full duplex file cloud (not distributed, but single server) that implements a custom protocol communicating data with sockets and encrypting it using a public/private key as certificate and encryption method. It is a passion project, which aims to repurpose an old phone to act as a cloud (not distributed, but single server) for myself. This doesn't spawn from a practical need of such a device, but rather to give myself a project to work on.
<br>
<br>
<br>

# Implementation

## Connection
To ensure a connection doesn't stay open after an abrupt crash from the other side, a timeout (`setSoTimeout()`) of [`READ_TIMEOUT`](ServerTelefono/src/fredver/constants/Constants.java) milliseconds is set to the reading `Socket`. To keep the connection alive, a heartbeat with an interval of [`HEATBEAT_INTERVAL`](ServerTelefono/src/fredver/constants/Constants.java) milliseconds is set.
<br>
<br>
## Security
I use `Socket` to ensure a secure communication, the server uses an implementation of a self-signed certificate, which is simply a public/private key pair, the server keeps the private key, the public key is given by the server administrator and manually set up in the client. This is to protect the server from eavesdropping and MITM attacks, making all connections private. No need for a `SSLSocket`, since endpoint encryption is implemented with the keys. See the [First connection](#first-connection) section<br>
<br>
I don't even want to think about implementing a defense against DDoS attacks, and that is mainly for these reasons:
  - It requires cutting edge implementations of complex technologies, out of the bugdget of this project;
  - Data is privately transported between client and server, so no hacker can read it, and that is what matters. If this project will expand to the point that it will be used to run important services that could be the object of an attack, then the appropriate security measures will be surely implemented.
To make a server generate a new certificate, a message with header title [`NEW_CERTIFICATE`](ServerTelefono/src/fredver/ioutils/HeaderTitle.java) is sent (see the [Header titles](#header-titles) section), and an answer with the new key is sent. At restarting, the server will use the new key.

<br>
<br>

## Custom protocol
In the [fredver.ioutils](ServerTelefono/src/fredver/ioutils) package there is the implementation of the classes and enums used to implement my own protocol.

### First connection
On first connection of the client with the server, a session key pair is generated by the client, and stored for use during that session.
Then:
  1. A message with header title [`PUBLIC_KEY`](ServerTelefono/src/fredver/ioutils/HeaderTitle.java) is sent by the client, encrypting it using the already known server's public key, where the header body is the public key of the client (See the [Header titles](#header-titles) section) for the server to use by encrypting messages
  2. The server receives the message, creates a new socket to manage the connection, and puts it in its map that associates socket connections with the public keys of those connections, without specifying the public key section, leaving it null, for now at least.
  3. The server reads the message, and, if the public key format is valid, then:
     - it takes the public key, and stores it in its map that associates socket connections with the public keys of those connections, putting it as the value of the map's entry of the sender of the public key's socket connection.

Now the server and the client have established a secure bridge of connection that protects them from eavesdropping and MITM attacks.

### Structure 
To have a formal definition of what is a valid message, it has to match this regex:
```
^([a-fA-F0-9]{32})\|([0-9]{1,15})\|([^|]{1,50})\|([^|]{1,4096})\|(.+)$
```

Which represents none other than a string structured, in the order the fields appear in the following table, all separated by the [`MESSAGE_HEADER_FIELDS_SEPARATOR`](ServerTelefono/src/fredver/constants/Constants.java). 
|Field name|Represented information's type|Represented information's size|Description|
|---|---|---|---|
|UUID|Hexadecimal string|Exactly 32 characters|An UUID of the message. The client generates it, the server echoes the one of the client's request it is responding to|
|Raw data length|Number|1 to 15 digits|Used to indicate the size of the raw data section of the message, used to determine if there is enough storage to hold a file or folder and send an alert message to the client if there is not enough storage, and has no other purpose|
|Header title|String|1 to 50 characters|To indicate the nature of the message, choosing from header titles as specified in the [`HeaderTitle`](ServerTelefono/src/fredver/ioutils/HeaderTitle.java) enum|
|Header body|String|1 to 4096 characters|To specify information about what the header title is about. Example: if the header title was `PATH_OPERATION`, then the header body could be [`HEADER_BODY_RMDIR`](ServerTelefono/src/fredver/ioutils/Header.java)|
|Raw data|Base85 encoded byte stream|The maximum number of bytes representable with the maximum number of digits of the field "Raw data length"|To send raw data through the connection, because all other communication information, for example the metadata, is put in the header|

A message ends and a new one starts when [`MESSAGE_END_SEPARATOR`](ServerTelefono/src/fredver/constants/Constants.java) is reached. It is not put in the valid message's regex because the raw data can be several gigabytes of data, and it obviously won't be sent all at once, so in some cases there's no way to establish whether there is a message ending character or not.<br>
<br>
The header body is of a maximum of 4096 characters because that's the max path of linux, and the header body is used, at its max length, to represent file paths.<br>


<br>
<br>

There can be only a header, and it has to have its their title match one of the titles' names specified by the [`HeaderTitle`](ServerTelefono/src/fredver/ioutils/HeaderTitle.java) enum. No field can ever not have a value, the default "non-value" is "null", as defined
by [`NULL_VALUE`](ServerTelefono/src/fredver/constants/Constants.java).
Each message it is sent in mind with the fact that the user is currently in a specific directory, so, for example, if a message with [`LIST_FILES_AND_DIRECTORIES`](ServerTelefono/src/fredver/ioutils/HeaderTitle.java) header is sent, then it will list all the files and directories in that specific directory.

### Encoding of raw data

When the server or the client are communicating raw data, base85 encoding of the files data is used. For the header, no encoding is used.<br>
Why is encoding used in the raw data section? Because sending raw data does not leave free characters to use as delimiters to differentiate between the different files' data and filenames, when transmitting multiple of them with the same message.<br>
<br>
Then why not Base64?<br>
Because it adds padding (`=` characters) if encoded bytes are not a multiple of `3`, and this has the consequence that it will not be distinguishable from actual data, because it flows like a stream from one socket to the other.<br>To avoid the padding's creation, you would have to load the whole file to send into memory, then encode it, so that the server has load it all into memory and then revert it back to normal encoding. That is obviously not possible, as large files could be sent over the network, and for the server to load the whole message into memory to decode it would require too much ram.<br>
Base85, also, reduces the space occupied by data, so that it is faster to transmit via sockets.<br>
<br>
The data of one or multiple files, is structured inside the raw data section as per the folllowing explanation:
  - The file name and data are separated by the constant [`FILE_NAME_AND_FILE_DATA_SEPARATOR`](ServerTelefono/src/fredver/constants/Constants.java), which is not a value produced by base85 encoding.
  - A file's data and the next file's name are separated by the constant[`FILE_DATA_AND_NEXT_FILE_NAME_SEPARATOR`](ServerTelefono/src/fredver/constants/Constants.java), which is not a value produced by base85 encoding.

<br>

### Header titles
Here is a comprehensive table with all possible values header titles values, as defined in [`HeaderTitle`](ServerTelefono/src/fredver/ioutils/HeaderTitle.java), and how they are used based on if the server or client sends it.<br>
<br>
There can be various answers in the body, they can be those defined in [fredver.ioutils.Header](ServerTelefono/src/fredver/ioutils/Header.java), or [fredver.constants.NULL_VALUE](ServerTelefono/src/fredver/constants/Constants.java).

|------|If client sends it|if server sends it|
|:---:|:--- |:--- |
||**`LIST_FILES_AND_DIRECTORIES`**|**`LIST_FILES_AND_DIRECTORIES`**|
|Header body:|The directory name to list the files of|It is a response from this same request from the client.<br>Possible values:<br>-`HEADER_BODY_GRANTED`<br>-`HEADER_BODY_INVALID_HEADER_FORMAT`<br>-`HEADER_BODY_NOT_AUTHORIZED`|
|Raw data:|`NULL_VALUE`|if granted:<br>-The names of the files and directories, as specified in the [Receiving and sending files](#receiving-and-sending-files) section<br><br>else:<br>-`NULL_VALUE`|
|---|---|---|
||**`GET_FILE_OR_FOLDER`**|**`GET_FILE_OR_FOLDER`**|
|Header body:|The directory or file name to get the files of|It is a response from this same request from the client.<br>Possible values:<br>-`HEADER_BODY_GRANTED`<br>-`HEADER_BODY_INVALID_HEADER_FORMAT`<br>-`HEADER_BODY_NOT_AUTHORIZED`<br>-`HEADER_BODY_NON_EXISTENT_FOLDER`|
|Raw data:|`NULL_VALUE`|if granted:<br>-The data of the specified file or folder as specified in the [Receiving and sending files](#receiving-and-sending-files) section<br><br>else:<br>-`NULL_VALUE`|
|---|---|---|
||**`PUBLISH_FILE_OR_FOLDER`**|**`PUBLISH_FILE_OR_FOLDER`**|
|Header body:|The published file or folder's desired name to have in the server's file system|It is a response from this same request from the client.<br>Possible values:<br>-`HEADER_BODY_GRANTED`<br>-`HEADER_BODY_NOT_ENOUGH_STORAGE`<br>-`HEADER_BODY_INVALID_HEADER_FORMAT`<br>-`HEADER_BODY_NOT_AUTHORIZED`|
|Raw data:|The data of the files and folders to publish, as specified in the [Receiving and sending files](#receiving-and-sending-files) section|`NULL_VALUE`|
|---|---|---|
||**`NEW_CERTIFICATE`**|**`NEW_CERTIFICATE`**|
|Header body:|`NULL_VALUE`|Possible values:<br>-`HEADER_BODY_GRANTED`<br>-`HEADER_BODY_NOT_AUTHORIZED`<br>-`HEADER_BODY_INVALID_HEADER_FORMAT`|
|Raw data:|`NULL_VALUE`|If granted:<br>-The new certificate's data (see the [Security](#security) section)<br><br>else:<br>-`NULL_VALUE`|
|---|---|---|
||**`PATH_OPERATION`**|**`PATH_OPERATION`**|
|Header body:|The operation to be executed.<br>Possible values:<br>-`HEADER_BODY_CD`<br>-`HEADER_BODY_RMDIR`<br>-`HEADER_BODY_DEL`|It is a response from this same request.<br>Possible values:<br>-`HEADER_BODY_INVALID_HEADER_FORMAT`<br>-`HEADER_BODY_GRANTED`<br>-`HEADER_BODY_NOT_AUTHORIZED`<br>`HEADER_BODY_NON_EXISTENT_FOLDER`|
|Raw data:|The file or folder name to be operated on|`NULL_VALUE`|
|---|---|---|
||**`PUBLIC_KEY`**|**`PUBLIC_KEY`**|
|Header body:|`NULL_VALUE`|`NULL_VALUE`|
|Raw data:|The public key of the client, on first connection only (otherwise server sends error on socket and terminates connection), as specified in the [Structure](#structure) section|The public key of the server, as specified in the [Structure](#structure) section|
|---|---|---|
||**`ASK_INFORMATION_TO_SERVER`**|**`ASK_INFORMATION_TO_SERVER`**|
|Header body:|The type of information to ask.<br>Possible values:<br>-`HEADER_BODY_ASK_CURRENT_PATH`<br>-`HEADER_BODY_ASK_ALLOCATED_STORAGE_TO_THIS_USER`|It is a response from this same request.<br>Possible values:<br>-`HEADER_BODY_GRANTED`<br>-`HEADER_BODY_INVALID_HEADER_FORMAT`|
|Raw data:|`NULL_VALUE`|If the header body was:<br>-`HEADER_BODY_ASK_CURRENT_PATH` then the absolute path to folder the user is currently in.<br>-`HEADER_BODY_ASK_ALLOCATED_STORAGE_TO_THIS_USER` then the bytes allocated to the user, then the [`ALLOCATED_STORAGE_INFORMATION_SEPARATOR`](ServerTelefono/src/fredver/constants/Constants.java), then the bytes the user is using currently, then the [`ALLOCATED_STORAGE_INFORMATION_SEPARATOR`](ServerTelefono/src/fredver/constants/Constants.java), then the remaining bytes|


<br>
<br>

## Console
The server through the console can be able to:
  - Run in online or offline mode
  - Manage its online status
  - Manage its users
  - Navigate through the folders and manage them
  - Manage the active connections
  - Benchmark and get the specs of the machine the server is running on
  - Change or set up a new certificate of the server manually. 
<br>
The console can be able to be accessed remotely, you just have to be logged in as administrator.<br>
The console can run in offline mode, but has to be specified at startup. In this mode you can manage files, users, and benchmark and get the specs of the machine the server is running on.


<br>
<br>

## Client
The client is able to:
- To connect to the server specifying its ip address and port, with a functionality to save those info for later reuse, and change them to a different one.
- Access the console if it is an admin
- Change or set up a new certificate of the server and get it.
- On its dedicated folder:
  - Navigate through the folders 
  - Get files and directories
  - Upload files and directories
  - Create files and directories

<br>
<br>

## Storage
Each user will have its partition, a folder with their name as name of the folder. If, for any writing operation, storage is not enough, it will delete the data that was writing, and a message with ERROR_CODE title and HEADER_BODY_NOT_ENOUGH_STORAGE body is sent to the client.<br>

- **TODO GIVE 5MB OF STORAGE OR SOME OTHER SMALL AMOUNT TO THE SERVER FOLDER, SO THAT IT CAN KEEP ALL INFO ABOUT THE USERS, AND THE CERTIFICATE**

<br>
<br>

## User management
**TODO**
There may be multiple users, each having their dedicated folder under the users folder. Each user has a password and username, which they have to use to connect to the server. Each user has their access level, specified as a boolean isAdministrator, in the file with name the string defined by [`SERVER_USERS_INFO_NAME`](ServerTelefono/src/fredver/constants/Constants.java). Each user can only interact with the contents of its user folder, unless it's the admin.<br>
The admin can set a limit, which is ***highly recommended***, of how much storage a user has allocated to himself (his folder). 

<br>
<br>


## Concurrency
>[!WARNING]
Just in case there is a bug, always keep at least two users with the maximum permissions if you plan on using the server remotely, since if one connection, for some reason, is not closed properly, at least with the other one you can restart the server and not lose access, since if the server thinks that you (the main admin account) are connected it won't let you connect again.

This is ***very*** important to keep the synchronization, the same user can only have one active connection with the server, ***not more***.
Only one client can connect to the server with a specific user, so only one instance of the same user can be connected to the server at the same time. If more try to connect the server simply refuses from the second request onward, and does not accept new logins until the active connection is terminated. 
Multiple connections to the server may be opened at same time, and the server has to process them all at the same time.
Messages can be sent at any moment from the clients and from the server, so they both have to be ready to process all messages at all times. This application implements a full duplex.

: USEFUL INFORMATION IN THIS NEXT CODE BLOCK**
```
Users with low permissions can only access their folder, so no conflict can come from them, unless an admin starts doing stuff on their folders while they are doing stuff too, like deleting and reading at the same time, for example. Read, modify, and write operations could clash. Read operations could read from a file that is being deleted, or try to read while a file is being deleted, so they might be then doing operations on a file that doesn't exist anymore. Two of the same file could be tried to be created at the same time, and this could be a problem.
In substance:
- When creating a file, you have to block read, delete, and create operations to that file specifically.
- When you read from a file, you cannot delete the file until it has finished reading. You can obviously not create it, as it already exists
- When deleting a file, you have to block reading and deleting that file. Then you have to make it so that the aforementioned operations will not give problems now that the file that is object of those operations does not exist anymore. You obviously cannot create it since it already exists.
```

The server uses a `java.util.HashSet` to keep track of all active connections, as it yields the best performances when randomly removing elements.
