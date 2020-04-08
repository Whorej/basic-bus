# **_basic-bus_**

## Introduction
basic-bus is a lightweight java event bus that uses the publish-subscribe naming convention. It is designed with **readability**, **usability** and **expandability**/**adaptation** in mind not **speed**.

## Usage
#### Instantiation
To instantiate the event bus simply create an instance.
<br>
```java
public final class SomeClass {

    public final BaseEventBus basicBus;
          
    public SomeClass() {
        basicBus = new BaseEventBus();
    }
}
```

#### Subscribe a Listener
To subscribe a listener firstly instantiate the event bus as shown above, then call the subscribe method with the listener you want to subscribe as the parameter.
```java
public final class SomeClass {

    public final BaseEventBus basicBus;
          
    public SomeClass() {
        basicBus = new BaseEventBus();

        basicBus.subscribe(new SomeListener());
    }
}
```

#### Use a Listener
To use a listener it must implement the IListener interface (not to be confused with the Listener annotation) and it's method isActive() must return true.
```java
public final class SomeListener implements IListener {

    @Listener(SomeEvent.class)
    public final void onSomeEvent() {    
        System.out.println("SomeEvent was called.");   
    }
    
    @Override
    public boolean isActive() {
        return true;
    }   

}
```
Alternatively you could change true to false and the listener would not be active. When the listener is not active the SomeEvent event will not be 'listened' for and therefore the onSomeEvent method will no be invoked.
