<p align="center">
  <img src="https://raw.githubusercontent.com/Haulmont/jmix-petclinic/main/img/petclinic_logo_with_slogan.svg"/>
</p>


# Jmix Petclinic - Intercom


## Jmix Petclinic Intercom Widget Integration

### 1. Custom WidgetSet

Replace 

```groovy

dependencies {
    implementation 'io.jmix.ui:jmix-ui-widgets-compiled'
}
```

with:
```groovy
dependencies {
    implementation 'io.jmix.ui:jmix-ui-widgets'
    widgets 'io.jmix.ui:jmix-ui-widgets'
}
```

### 2. Create Intercom Integration

(based on https://github.com/wbadam/Intercom)

* [IntercomIntegration Connector](src/main/java/io/jmix/petclinic/widgets/IntercomIntegration.java)
* [IntercomState](src/main/java/io/jmix/petclinic/widgets/client/IntercomState.java)
* [intercom.js](src/main/resources/io/jmix/petclinic/widgets/intercom.js)


## Owner Sync

Owners are synced from the Application via the Intercom REST API. The sync is done on a schedule (every minute).

The logic uses the Contact Search API to find contacts in Intercom based on the external ID or the Email. If any 
of those match, the contact will be updated. Otherwise, a new Contact will be created.

![Owner Sync](img/owner-sync.png)


## Show upcoming Visits in the Intercom inbox

Another example of an integration is to show specific data inside the inbox of Intercom. This way, the call center agents
can see relevant information directly next to the conversation and therefore improve the quality / speed of the conversation.

The example use-case is the following: 

When a Nurse user has a question about a particular upcoming visit, they can reach out via chat to the customer care agents.
