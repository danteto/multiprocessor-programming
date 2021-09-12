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

For testing purposes I created simple Counter class that has method increment() and tests that check if the implementation is correct.
We will test each our locking implementation on this class.

Let's take a look at first simple lock algorithm that has practical usage: TestAndTestSetLock.
For the implementation of the algorithm we need atomic boolean "register" and Read-Modify-Write operation, namely, getAndSet(). GetAndSet() operation updates a register with the given value, but return old value.
At the beginning the register value is false. In lock() method it is just spinning while the GetAndSet operation returns true, otherwise it enters a critical section. In unlock() method it set false value.    