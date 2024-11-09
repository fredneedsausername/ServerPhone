
> [!WARNING]
> Documentation is only partly completed, there might be changes to existing parts and surely new things will be added, especially those marked with **TODO**


**Bug if the socket buffer size is less than the metadata information dimensions**

`NOT_SUFFICIENT_STORAGE`**TODO THINK OF WHAT HAPPENS IF THERE IS NOT ENOUGH STORAGE IN THE SERVER TO STORE THE FILES: WILL IT continue accepting the input? What will it do with the rest of the received input? will it throw it away? what will it do with the file it started writing in its memory? ALSO MEMORY COULD BE SUFFICIENT AT THE START OF THE REQUEST BUT COULD NOT BECOME SUFFICIENT AS THE DATA IS SENT, SO YOU HAVE TO SEND A NOT_SUFFICIENT_STORAGE MESSAGE IN THAT SITUATION, TOO, AND ELIMINATE THE FILES THAT WERE GENERATING TODO THINK OF WHAT HAPPENS IF THERE IS NOT ENOUGH STORAGE IN THE SERVER TO STORE THE FILES: WILL IT continue accepting the input? What will it do with the rest of the received input? will it throw it away? what will it do with the file it started writing in its memory?**


**TODO UPDATE HEADER_BODY_NEGATED WITH HEADER_BODY_REFUSED**

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
  - To the scope of this server, the only true danger there is is not having an encrypted connection if using the server to store sensitive data, because surely nobody is interested in actuating an attack just to stop the service on a random small media center. Anyways, i'm doing it for myself, and i know that i will not be subject to these kinds of attacks; if someone wants to use this too for their own needs, then they probably don't need a super-secure server, otherwise they would have probably chosen to use a different software


<br>
<br>

## Installation
The server is installed in a certain folder, which is the folder where the executable is located. To start up the server from another location please use a shortcut. Here it will keep information about its users, and the data.

## Custom protocol
In [fredver.ioutils](ServerTelefono/src/fredver/ioutils) package there is the implementation of the classes used for my own protocol.
### Structure 
To have a formal definition of what is a valid message, it has to match this regex: 
```
^([^;]+);([^:]+):([^|]+)\|(.+)$
```
Which represents none other than a string structured like this:
```
raw-data-length;header-title:header-body|raw-data
```
The raw-data-length information is used to differentiate between message and message, so that it reads up to the message's length and that's the data.
<br>**TODO WHAT HAPPENS IF IT READS LESS OR MORE OR IF THE LENGTH IS ZERO OR NEGATIVE. IT HAS TO BE KEPT IN A `BigInteger` BECAUSE INT IS LIMITED TO 2 BILLION, WHICH IN BYTES IS 2 GIGS: BUG**<br>
There can be only a header, and it has to have its their title match one of the titles' names specified by the [`HeaderTitle`](ServerTelefono/src/fredver/ioutils/HeaderTitle.java) enum. No field can ever not have a value, the default "non-value" is "null", as defined in [`Constants.NULL_VALUE`](ServerTelefono/src/fredver/constants/Constants.java)

### Header titles
Here is a comprehensive table with all possible values header titles values, as defined in [`HeaderTitle`](ServerTelefono/src/fredver/ioutils/HeaderTitle.java), and how they are used based on if the server or client sends it.
<br> **TODO UPDATE the table removing NOT_AUTHORIZED REFERENCES and updating and completing** 
**structural update to the whole header thing. removal of not_sufficient_storage, it will be put with header_body_granted and header and header_body_refused**<br>

|Header title|If client sends it|if server sends it|
|:---:|:---:|:---:|
|---|`LIST_FILES_AND_DIRECTORIES`|---|
|Header title|||
|Header body|||
|Raw data|||
|---|---|---|
||`GET_FILE_OR_FOLDER`||
|Header title|||
|Header body|||
|Raw data|||
|---|---|---|
||`PUBLISH_FILE_OR_FOLDER`||
|Header title|||
|Header body|||
|Raw data|||
|---|---|---|
||`NEW_TLS_CERTIFICATE`||
|Header title|||
|Header body|||
|Raw data|||

**TODO THINK OF WHAT HAPPENS IF THERE IS NOT ENOUGH STORAGE IN THE SERVER TO STORE THE FILES: WILL IT continue accepting the input? What will it do with the rest of the received input? will it throw it away? what will it do with the file it started writing in its memory?**|**TODO THINK OF WHAT HAPPENS IF THERE IS NOT ENOUGH STORAGE IN THE SERVER TO STORE THE FILES: WILL IT continue accepting the input? What will it do with the rest of the received input? will it throw it away? what will it do with the file it started writing in its memory?**|


**TODO RETURN VALUES DEL CLIENT E DEL SERVER SE VENGONO MANDATI QUESTI MESSAGGI**


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
- access the console if it is an admin, otherwise a specific menu based on its authorization level. See [`fredver.clientserver.PermissionLevel`](ServerTelefono/src/fredver/clientserver/PermissionLevel.java) enum.
- change or set up a new tls certificate of the server and get it. **TODO: FIGURE HOW THE HELL TO DO THIS -----------------------**
- based on its certification level:
  - navigate through the folders 
  - get files and directories
  - upload files and directories
  - create files and directories

<br>
<br>

## User management
**TODO**
There may be multiple users, each having their dedicated folder under the users folder. Each user has a password and username, which they have to use to connect to the server. Each user has their access level, from the defined ones in [`fredver.clientserver.PermissionLevel`](ServerTelefono/src/fredver/clientserver/PermissionLevel.java) enum. Each user can only interact with the contents of its user folder, unless it's the admin.<br>
The admin can set a limit, which is ***highly recommended***, of how much storage a user has allocated to himself (his folder). If this limit is exceeded when creating or uploading new files or folders, then the operation is cancelled, and the message with the header title [`NOT_SUFFICIENT_STORAGE`](ServerTelefono/src/fredver/ioutils/HeaderTitle.java) is sent to the server. **TODO IMPLEMENT THIS ----------------------------** **TODO NOT_SUFFICIENT_STORAGE NOT USED ANYMORE**

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
