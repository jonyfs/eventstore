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

## Continuous Integration
[![Codeship Status for jonyfs/eventstore](https://app.codeship.com/projects/2c9c90e0-905c-0136-a649-22ecb9c20d15/status?branch=master)](https://app.codeship.com/projects/304106)

## Continuous Code Quality
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=jonyfs_eventstore&metric=alert_status)](https://sonarcloud.io/dashboard?id=jonyfs_eventstore)
[![Security](https://sonarcloud.io/api/project_badges/measure?project=jonyfs_eventstore&metric=security_rating)](https://sonarcloud.io/api/project_badges/measure?project=jonyfs_eventstore&metric=security_rating)
[![Maintainability](https://sonarcloud.io/api/project_badges/measure?project=jonyfs_eventstore&metric=sqale_rating)](https://sonarcloud.io/api/project_badges/measure?project=jonyfs_eventstore&metric=sqale_rating)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=jonyfs_eventstore&metric=coverage)](https://sonarcloud.io/api/project_badges/measure?project=jonyfs_eventstore&metric=coverage)


##Tasks:
* Write tests;
* Provide some evidence of thread-safety;
* Justify design choices, arguing about costs 
  and benefits involved. You may write those as comments 
  inline or, if you wish, provide a separate document 
  summarizing those choices;
* Write all code and documentation in english.