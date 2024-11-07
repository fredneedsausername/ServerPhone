# ServerPhone
_Believe it or not, this is the third time i delete this repo because of configurations issues, which took me a day to sort out.<br>
I finally managed to publish my work here, i will keep you posted here._

## Purpose
This client/server application is a media server and a passion project, which aims to repurpose an old phone to act as a cloud (i know it's not technically a cloud because it's not distributed) for me and also, in future, if concurrency is added, to act as a NAS for me and my father. Not that we need it, i just think it's cool to have a NAS i myself built from scratch (not from scratch like extracted the silica but you're getting the point).

# Implementation

## Security

I use `SSLSocket` to ensure a secure communication, the server using a custom certificate not issued by a CA to protect against MITM attacks, so you have to know the server administrator and ask him to give you the certificate verification information. This is to ensure the server can run with complete protection from transmission data stealing attacks, even when it doesn't have a domain (so that a certificate can be issued by a CA), considering that in most cases contacting the administrator will be possible.<br>
<br>
The `SSLSocket` uses **TODO** encryption details<br>
<br>
Certificates are generated using **TODO** technology<br>
<br>
I use **TODO** technology to protect from replay attacks<br>
<br>
There is to consider also that it might be necessary to implement validation of all incoming data to prevent attacks like buffer overflow or injection attacks, always with the objective of protecting against transmission data stealing attacks.<bt>
<br>
I don't even have to think about implementing a defense against DDoS attacks, and that is mainly for these reasons:
  - It might require technologies that are too complicated to be studied and implemented, but i wouldn't know because i didn't study them;
  - To the scope of this server, the only true danger there is is not having an encrypted connection if using the server to store sensitive data, because surely nobody is interested in actuating an attack just to stop the service on a random small media center. Anyways, i'm doing it for myself, and i know that i will not be subject to these kinds of attacks; if someone wants to use this too for their own needs, then they probably don't need a super-secure server, otherwise they would have used a software from a reputable source, which i am not.


<br>
<br>
<br>

## Custom protocol
In fredver.ioutils package there is the implementation of the classes used for my own protocol. Messages roughly follow this structure:
```
headertitle1:headerbody1;headertitle2:headerbody2;headertitleN:headerbodyN|rawdata
```
There can be as many headers as you want, but they have to each have their title match one of the titles' names specified by the ```RequestType``` enum. No field can ever not have a value, the default "non-value" is "null", as defined in `Constants.NULL_VALUE` <br>
To have a formal definition of what is a valid message, it has to match this regex: `"^([^:;]+:[^:;]+(?:;[^:;]+:[^:;]+)*)\\|(.+)$"`

There are four static Strings defined in `fredver.ioutils.Message`, which are Pre-compiled messages you can use to signal connection termination:

| Pre-compiled message String name | Message  |
| ------------- | ------------- |
| clientToServerDisconnectionMessage  | to be sent by client to server to signal they request to terminate the connection  |
| serverApprovedClientDisconnectionMessage  | to be sent by the server to signal if it ready to cut off connection as requested by client  |
| serverDeniedClientDisconnectionMessage | to be sent by the server to signal if it ready to cut off connection as requested by client |
| serverToClientWantsToShutOffMessage | to be sent by the server to the client to signal it wants to shut off, so to not send data anymore |

You can get and use these values with the respective methods that start with `get`, and terminate with the name of the pre-compiled String.


<br>
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
<br>
The console can be able to be accessed remotely, you just have to enter the remote login credentials.



<br>
<br>
<br>


## User management
**TODO**
///////
per il login e password, il Server chiede al client una password e un username, poi se è giusta la password legata all'username mette il socket in una map con una classe per identificare le informazioni della sessione, che quando termina verrà rimossa dalla map.
///////
different permissions
///////


<br>
<br>
<br>


## Concurrency
**TODO**
///////
the first release will be with only one connection at a time, with plans to expand it to multiple concurrent connections in the future.
///////

