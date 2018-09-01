# EventStore Example

In this example, I created a class that implements the `EventStore`
interface.
 
```java
public interface EventStore {
    void insert(Event event);

    void removeAll(String type);

    EventIterator query(String type, long startTime, long endTime);
}
```

Tasks:
* Write tests;
* Provide some evidence of thread-safety;
* Justify design choices, arguing about costs 
  and benefits involved. You may write those as comments 
  inline or, if you wish, provide a separate document 
  summarizing those choices;
* Write all code and documentation in english.