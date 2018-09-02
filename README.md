# EventStore Example

In this example, I created a class that implements the `EventStore`
interface.
 
```java
public interface EventStore {
    void insert(Event event); 

    void removeAll(String type);

    EventIterator query(String type, LocalDateTime startTime, LocalDateTime endTime);
}
```

## Continuous Integration
[![Codeship Status for jonyfs/eventstore](https://app.codeship.com/projects/2c9c90e0-905c-0136-a649-22ecb9c20d15/status?branch=master)](https://app.codeship.com/projects/304106)

## Continuous Code Quality


[![Sonar Cloud](https://sonarcloud.io/images/project_badges/sonarcloud-white.svg)](https://sonarcloud.io/dashboard?id=jonyfs_eventstore)


[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=jonyfs_eventstore&metric=alert_status)](https://sonarcloud.io/dashboard?id=jonyfs_eventstore)
[![Security](https://sonarcloud.io/api/project_badges/measure?project=jonyfs_eventstore&metric=security_rating)](https://sonarcloud.io/dashboard?id=jonyfs_eventstore)
[![Maintainability](https://sonarcloud.io/api/project_badges/measure?project=jonyfs_eventstore&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=jonyfs_eventstore)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=jonyfs_eventstore&metric=coverage)](https://sonarcloud.io/dashboard?id=jonyfs_eventstore)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=jonyfs_eventstore&metric=bugs)](https://sonarcloud.io/dashboard?id=jonyfs_eventstore)



## About it:

The Test Results,to verify if the code implementations was correct, could be verifid in in [SonarCloud](https://sonarcloud.io/dashboard?id=jonyfs_eventstore)