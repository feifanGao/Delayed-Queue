Delayed Queue
A queue is a data structure where the element removed is always the oldest element (the one which has been waiting the longest) out of the remaining elements, ie. the one which was added the least recently. See: Queue (abstract data type) - Wikipedia. The two main operations are enqueue(E) which adds an element and dequeue() which removes an element.

A DelayedQueue works like a normal queue except has a restriction ('delay condition') that prohibits elements from being removed until a certain number of enqueue operations have occurred. Once the required number of enqueue operations occur, any number of elements may be removed, however the moment another element is added, the delay condition comes back into force. 

Your task is to create a class called MyQueue that implements the generic DelayedQueue interface according to the specification written in the docstrings for each method. Your class should be able to be instantiated with

DelayedQueue<...> s = new MyQueue<...>(7);
where the ... can be replaced by any object, and the int parameter for the constructor represents the max delay value (ie. number of enqueue operations that must occur before dequeue operations can start to occur).

If the max delay value is changed, the change does not take effect until the next time the delay is reset to the maximum (ie. when enqueue occurs after a sequence of dequeue operations).
