
> [!WARNING]
> Documentation is only partly completed, there might be changes to existing parts and surely new things will be added, especially those marked with **TODO**

**TODO THINK OF WHAT HAPPENS IF THERE IS NOT ENOUGH STORAGE IN THE SERVER TO STORE THE FILES: WILL IT continue accepting the input? What will it do with the rest of the received input? will it throw it away? what will it do with the file it started writing in its memory? ALSO MEMORY COULD BE SUFFICIENT AT THE START OF THE REQUEST BUT COULD NOT BECOME SUFFICIENT AS THE DATA IS SENT, SO YOU HAVE TO SEND A NOT_SUFFICIENT_STORAGE MESSAGE IN THAT SITUATION, TOO, AND ELIMINATE THE FILES THAT WERE GENERATING TODO THINK OF WHAT HAPPENS IF THERE IS NOT ENOUGH STORAGE IN THE SERVER TO STORE THE FILES: WILL IT continue accepting the input? What will it do with the rest of the received input? will it throw it away? what will it do with the file it started writing in its memory?**
**TODO I WANT TO USE BASE85 ENCODING TO TRANSMIT DATA, UPDATE DOC AND IMPLEMENT**
**TODO PROTECT FROM USERS TRYING TO ACCESS FOLDERS THAT ARE NOT THEIRS**
**TODO ARE WRITING OPERATIONS BLOCKING, IN THE SENSE THAT IF SOMETHING IS WRITING TO A FILE, THE WHOLE APPLICATION WILL STAND STILL AND WAIT THAT OTHER PROCESS TO FINISH?**
**TODO ADD A WAY TO NAVIGATE THROUGH FILES WITH A CLIENT**
**TODO should i add eclipse configuration files in the .gitignore? Are they necessary for java to work? Are they specific to eclipse? How to compensate for people that use a different ide, and need those configurations files, too? Is there a way to not publish on GitHub those files at all?**
**TODO REFACTOR AUTHORIZATIONLEVEL WITH A BOOLEAN ISADMIN**
**TODO ADD A UUID TO EVERY MESSAGE SO THAT IT DOESN'T GO CONFUSED, UPDATE TABLE OF HEADER TITLES TO, INSTEAD OF HAVING AS HEADER TITLE A REQUEST, HAVE THE UUID OF THE REQUEST**
**TODO GIVE 5MB OF STORAGE OR SOME OTHER SMALL AMOUNT TO THE SERVER FOLDER, SO THAT IT CAN KEEP ALL INFO ABOUT THE USERS, AND THE CERTIFICATE**
<br>
# ServerPhone
_Believe it or not, this is the third time i delete this repo because of configurations issues, which took me a day to sort out.<br>
I finally managed to publish my work here, i will keep you posted here._<br>
<br>
Special thanks go to classical, jazz, phonk, and rock music for keeping me company during the creation of this little project.<br>
<br>
This project supports full JavaDoc documentation, so feel free to consult it.
## Purpose
This client/server application is a media server and a passion project, which aims to repurpose an old phone to act as a cloud (i know it's not technically a cloud because it's not distributed) for me and also, in future, if concurrency is added, to act as a NAS for me and my father. Not that we need it, i just think it's cool to have a NAS i myself built from scratch (not from scratch like extracted the silica but you're getting the point).

<br>
<br>
<br>

# Implementation

## Connection
To ensure a connection doesn't stay open after an abrupt crash from the other side, a timeout (`setSoTimeout()`) of [`Constants.READ_TIMEOUT`](ServerTelefono/src/fredver/constants/Constants.java) milliseconds is set to the reading `SSLSocket`. To keep the connection alive, a heartbeat with an interval of [`Constants.HEATBEAT_INTERVAL`](ServerTelefono/src/fredver/constants/Constants.java) milliseconds is set
<br>
<br>
## Security
I use `SSLSocket` to ensure a secure communication, the server using a custom certificate not issued by a CA to protect against MITM attacks, so you have to know the server administrator and ask him to give you the certificate verification information. Not because it is secret, but to ensure that it is the right one and a MITM attack doesn't happen on first connection. This is to ensure the server can run with complete protection from eavesdropping, even when it doesn't have a domain (so that a certificate can be issued by a CA), considering that in most cases contacting the administrator will be possible.<br>
<br>
The `SSLSocket` uses **TODO** encryption details<br>
<br>
Certificates are generated using **TODO** technology<br>
<br>
I don't even have to think about implementing a defense against DDoS attacks, and that is mainly for these reasons:
  - It might require technologies that are too complicated to be studied and implemented, but i wouldn't know because i didn't study them;
  - To the scope of this server, the only true danger there is is not having an encrypted connection if using the server to store sensitive data, because surely nobody is interested in actuating an attack just to stop the service on a random small media center. Anyways, i'm doing it for myself, and i know that i will not be subject to these kinds of attacks; if someone wants to use this too for their own needs, then they probably don't need a super-secure server, otherwise they would have probably chosen to use a different software<br>
To make a server generate a new Certificate, a message with header [`NEW_CERTIFICATE`](ServerTelefono/src/fredver/ioutils/HeaderTitle.java) is sent (see the [Header titles](#header-titles) section), and an answer with the new key is sent, at restarting the new key will be used. **TODO IMPLEMENT THIS**


<br>
<br>

## Installation
The server is installed in a certain folder, which is the folder where the executable is located. To start up the server from another location please use a shortcut. Here it will keep information about its users, and the data.

## Custom protocol
In [fredver.ioutils](ServerTelefono/src/fredver/ioutils) package there is the implementation of the classes used for my own protocol.
### Structure 
To have a formal definition of what is a valid message, it has to match this regex:
```
^([a-fA-F0-9]{32})-([0-9]{1,3});([^;:\|]{1,50}):([^;:\|]{1,4096})\|(.+)$
```
Which represents none other than a string structured like this:
```
(this is a 32 character hexadecimal UUID)-(1 to three numbers, but not zero or any lea);(1 to 50 characters of header title):(1 to 4096 character of header body)|(whatever length of raw data)
```

Each message is identified by a UUID, that, if to send the message it is:
  - The user: then it's a radomly generated 32 hexadecimal string;
  - The server: the UUID of the message from the client it is responding to.

<br>
The header body is of a maximum of 4096 characters because that's the max path of linux, and the header body is used, at its max length, to represent file paths.<br>
<br>
The raw-data-length information is used to differentiate between message and message, so that it reads up to the message's length and that's the data.
<br> 

**TODO WHAT IT HAS TO BE KEPT IN A `BigInteger` BECAUSE INT IS LIMITED TO 2 BILLION, WHICH IN BYTES IS 2 GIGS: BUG** 
<br>
<br>
There can be only a header, and it has to have its their title match one of the titles' names specified by the [`HeaderTitle`](ServerTelefono/src/fredver/ioutils/HeaderTitle.java) enum. No field can ever not have a value, the default "non-value" is "null", as defined in [`Constants.NULL_VALUE`](ServerTelefono/src/fredver/constants/Constants.java). <br>
<br>
Each message it is sent in mind with the fact that the user is currently in a specific directory, so, for example, if a message with [`LIST_FILES_AND_DIRECTORIES`](ServerTelefono/src/fredver/ioutils/HeaderTitle.java) header is sent, then it will list all the files and directories in that specific directory.

### Receiving and sending files
if the server or the client are communicating raw file data, base85 encoding of the files data is used.<br>
Why? Because sending raw data does not leave free characters to use as delimiters to differentiate between the different files' data and filenames, when transmitting multiple of them with the same message.<br>
<br>
Then why not Base64?<br>
Because it adds padding (`=` characters) if encoded bytes are not a multiple of `3`, and this has the consequence that it will not be distinguishable from actual data, because it flows like a stream from one socket to the other.<br>To avoid the padding's creation, you obviously cannot load into memory the file in its whole to transmit it so that the server can load it all into memory and then revert it back to normal encoding, because that puts a memory burden that could not be supported by either the client or the server.<br>
Base85, also, reduces the space occupied by data, so that it is faster to transmit via sockets. This is the same optimization result of having a bigger buffer. <br> **
<br>
File data, or data of multiple files, is structured inside the raw data section as per the folllowing explanation:
  - The file name and data are separated by the constant [`fredver.constants.Constants.FILE_NAME_AND_FILE_DATA_SEPARATOR`](ServerTelefono/src/fredver/constants/Constants.java), which is not a value encoded by base85 encoding
  - A file's data and the next file's name are separated by the constant[`fredver.constants.FILE_DATA_AND_NEXT_FILE_NAME_SEPARATOR`](ServerTelefono/src/fredver/constants/Constants.java), which is not a value encoded by base85 encoding.

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
|Header body:|The directory or file name to get the files of|It is a response from this same request from the client.<br>Possible values:<br>-`HEADER_BODY_GRANTED`<br>-`HEADER_BODY_INVALID_HEADER_FORMAT`<br>-`HEADER_BODY_NOT_AUTHORIZED`|
|Raw data:|`NULL_VALUE`|if granted:<br>-The data of the specified file or folder as specified in the [Receiving and sending files](#receiving-and-sending-files) section<br><br>else:<br>-`NULL_VALUE`|
|---|---|---|
||**`PUBLISH_FILE_OR_FOLDER`**|**`PUBLISH_FILE_OR_FOLDER`**|
|Header body:|Folder name if there are multiple files and folders, File name otherwise|It is a response from this same request from the client.<br>Possible values:<br>-`HEADER_BODY_GRANTED`<br>-`HEADER_BODY_NOT_ENOUGH_STORAGE`<br>-`HEADER_BODY_INVALID_HEADER_FORMAT`<br>-`HEADER_BODY_NOT_AUTHORIZED`|
|Raw data:|The data of the files and folders to publish, as specified in the [Receiving and sending files](#receiving-and-sending-files) section|`NULL_VALUE`|
|---|---|---|
||**`NEW_CERTIFICATE`**|**`NEW_CERTIFICATE`**|
|Header body:|`NULL_VALUE`|Possible values:<br>-`HEADER_BODY_GRANTED`<br>-`HEADER_BODY_NOT_AUTHORIZED`<br>-`HEADER_BODY_INVALID_HEADER_FORMAT`|
|Raw data:|`NULL_VALUE`|If granted:<br>-The new certificate<br><br>else:<br>-`NULL_VALUE`|
|---|---|---|
||**`PATH_OPERATION`**|**`PATH_OPERATION`**|
|Header body:|The operation to be executed.<br>Possible values:<br>-`HEADER_BODY_CD`<br>-`HEADER_BODY_RMDIR`<br>-`HEADER_BODY_DEL`|It is a response from this same request.<br>Possible values:<br>-`HEADER_BODY_INVALID_HEADER_FORMAT`<br>-`HEADER_BODY_GRANTED`<br>-`HEADER_BODY_NOT_AUTHORIZED`<br>`HEADER_BODY_NON_EXISTENT_FOLDER`|
|Raw data:|The file or folder name to be operated on|`NULL_VALUE`|

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
  - Change or set up a new tls certificate of the server manually. **TODO: IMPLEMENT ON THE CONSOLE --------------------**
<br>
The console can be able to be accessed remotely, you just have to be logged in as administrator.<br>
The console can run in offline mode, but has to be specified at startup. In this mode you can manage files, users, and benchmark and get the specs of the machine the server is running on.


<br>
<br>

## Client
The client is able to:
- to connect to the server specifying its ip address and port, with a functionality to save those info for later reuse, and change them to a different one.
- access the console if it is an admin, otherwise a specific menu based on its authorization level. See [`fredver.clientserver.PermissionLevel`](ServerTelefono/src/fredver/clientserver/PermissionLevel.java) enum. **TODO REFACTOR THIS WITH A BOOLEAN**
- change or set up a new tls certificate of the server and get it. **TODO: FIGURE HOW THE HELL TO DO THIS -----------------------**
- based on its certification level:
  - navigate through the folders 
  - get files and directories
  - upload files and directories
  - create files and directories

<br>
<br>

## Storage
Each user will have its partition, a folder with their name as name of the folder. If, for any operation, storage is not enough, it will delete the data that was writing **TODO THINK OF WHAT HAPPENS IF THERE IS NOT ENOUGH STORAGE IN THE SERVER TO STORE THE FILES: WILL IT continue accepting the input? What will it do with the rest of the received input? will it throw it away? what will it do with the file it started writing in its memory?**

<br>
<br>

## User management
**TODO**
There may be multiple users, each having their dedicated folder under the users folder. Each user has a password and username, which they have to use to connect to the server. Each user has their access level, from the defined ones in [fredver.clientserver.PermissionLevel](ServerTelefono/src/fredver/clientserver/PermissionLevel.java) **TODO REFACTOR THIS WITH A BOOLEAN** enum. Each user can only interact with the contents of its user folder, unless it's the admin.<br>
The admin can set a limit, which is ***highly recommended***, of how much storage a user has allocated to himself (his folder). **TODO IMPLEMENT THIS ----------------------------**

<br>
<br>


## Concurrency
**TODO Ensure a connection never stays open when it should be closed, otherwise you have to restart the server.<br>
Just in case there is a bug, always keep at least two users with the maximum permissions if you plan on using the server remotely, since if one connection, for some reason, is not closed properly, at least with the other one you can restart the server and not lose access.**

This is ***very*** important to keep the synchronization, the same user can only have one active connection with the server, ***not more***.
Only one client can connect to the server with a specific user, so only one instance of the same user can be connected to the server at the same time. If more try to connect the server simply refuses from the second request onward, and does not accept new logins until the active connection is terminated. 
Multiple connections to the server may be opened at same time, and the server has to process them all at the same time.
Messages can be sent at any moment from the clients and from the server, so they both have to be ready to process all messages at all times. This application implements a full duplex.

**TODO FIGURE OUT HOW TO DO SYNCHRONIZED CONCURRENCY: USEFUL INFORMATION IN THIS NEXT CODE BLOCK**
```
Users with low permissions can only access their folder, so no conflict can come from them, unless an admin starts doing stuff on their folders while they are doing stuff too, like deleting and reading at the same time, for example. Read, modify, and write operations could clash. Read operations could read from a file that is being deleted, or try to read while a file is being deleted, so they might be then doing operations on a file that doesn't exist anymore. Two of the same file could be tried to be created at the same time, and this could be a problem.
In substance:
- When creating a file, you have to block read, delete, and create operations to that file specifically.
- When you read from a file, you cannot delete the file until it has finished reading. You can obviously not create it, as it already exists
- When deleting a file, you have to block reading and deleting that file. Then you have to make it so that the aforementioned operations will not give problems now that the file that is object of those operations does not exist anymore. You obviously cannot create it since it already exists.
```

The server uses a `java.util.HashSet` to keep track of all active connections, as it yields the best performances when randomly removing elements.
