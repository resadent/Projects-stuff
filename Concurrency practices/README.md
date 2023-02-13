In this folder I include some practices I did regarding concurrency in systems.

Bandeja (tray) is a place where cakes are put. There are two types of cakes: premium and normal ones, and the tray has several of both two. Clientes (consumers) are several threads that take cakes from the tray, and Reponedor (restocker) is another thread that whenever a type of cake runs out, restocks them so that there are more cakes available for the customers.

This algorithm uses mutexes and several other semaphores to make sure the critical sections stay correct and no one takes cakes when they run out, and data stays just consistent.
