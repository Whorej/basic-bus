# **_basic-bus_**

## Introduction
basic-bus is a lightweight java event bus that uses the publish-subscribe naming convention. It is designed with **readability**, **usability** and **expandability**/**adaptation** in mind not **speed**.

## Usage
#### Instantiation
To instantiate the event bus simply create an instance with the constructor.
```java
public final class SomeClass {

    public final BaseEventBus basicBus;
          
    public SomeClass() {
        basicBus = new BaseEventBus();
    }
}
```

#### Subscribe a Subscriber
To create a subscriber firstly instantiate the event bus as shown above, then call the subscribe method with the Object you want to subscribe as the parameter.
```java
public final class SomeClass {

    public final BaseEventBus basicBus;
          
    public SomeClass() {
        basicBus = new BaseEventBus();

        basicBus.subscribe(new SomeSubscriber());
    }
}
```

#### Use a Subscriber
Once an Object is subscribed any method within it that is annotated with Listener and has either no parameters or one which is the same type as the specified class in the Listener annotation.
```java
public final class SomeSubscriber {

    @Listener(SomeEvent.class)
    public final void onSomeEvent() {    
        System.out.println("SomeEvent was called.");   
    }

    @Listener(SomeOtherEvent.class)
    public final void onSomeOtherEvent(SomeOtherEvent event) {    
        System.out.println(event.getSomeField());   
    }

}
```

#### Publish an Event
Once you have instantiated the event bus and subscribed a subscriber you may now start publishing events. To publish an event (any Object) simply:

```java
public final class SomeClass {

    public final BaseEventBus basicBus;
          
    public SomeClass() {
        basicBus = new BaseEventBus();

        basicBus.subscribe(new SomeSubscriber());

        basicBus.publish(new SomeClassConstructedEvent(this));
    }
}
```
