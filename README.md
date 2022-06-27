<p align="center">
  <img src="https://raw.githubusercontent.com/Haulmont/jmix-petclinic/main/img/petclinic_logo_with_slogan.svg"/>
</p>


## Jmix Petclinic Intercom Integration


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
