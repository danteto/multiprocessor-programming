## Here you can find basic data structures and algorithms for multiprocessor programming in shared-memory multiprocessor environment.

Multiprocessor programming introduces many challenges.

The main tool to deal with concurrent programming is locks.

In java the framework of using locks is as follows:
```
mutex.lock();
try {
    ...         // body
    } 
finally {
    mutex.unclock();
}
```
It means that even if the code in try block throws an exception the mutex will be unlocked.

The interface of the Lock implementation used int this examples is:
```
public interface Lock {
    void lock();
    void unlock();
}
```