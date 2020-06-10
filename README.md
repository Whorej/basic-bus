# **_basic-bus_**

## What is it?
basic-bus is a lightweight java event bus that offers **speed**, **scalability** (the compiled JAR is only 5kb!) and **extensibility**.

## How do I get it?
Add to your gradle.build:
```
repositories {
    maven { url 'https://jitpack.io' }
}
        
dependencies {
    implementation 'com.github.Whorej:basic-bus:1.4.1'
}
```

## How do I use it?
### Instantiation
To instantiate the event bus simply create an instance with the constructor.
```java
eventBus = new BusImpl();
```
for an asynchronous implementation use:
```java
asyncBus = new Async();
```

### Subscribe a Subscriber
To create a subscriber firstly instantiate the event bus as shown above, then call the subscribe method with the Object you want to subscribe as the parameter.
```java
eventBus.subscribe(new SomeSubscriber());
```

### Use a Subscriber
Once an Object is subscribed any method within it that is annotated with Listener and has either no parameters or one which is the same type as the specified class in the Listener annotation.
```java
public class SomeSubscriber {

    /* whatever type is specified in the type parameter
       can be used here (in the Listener annotation parameter)
       or a child class of that type */
    @Listener(String.class)
    public final void onMessage() {
        System.out.println("Some message was sent.");   
    }

    @Listener(String.class)
    public final void onMessage(String message) {
        System.out.println("The message send was: " + message);   
    }

}
```

### Post an Event
Once you have instantiated the event bus and subscribed a subscriber you may now start posting events. To post an event (any Object) simply:

```java
// because of the String type parameter used when instantiating the Bus 
// we can parse string literals as the generic paramter in post
eventBus.post("Message that was sent");
```
