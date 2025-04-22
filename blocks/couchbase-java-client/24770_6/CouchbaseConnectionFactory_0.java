  class Resubscriber implements Runnable {

    ConfigurationProvider newConfigProvider;
    Resubscriber(ConfigurationProvider newConfigProvider){
      this.newConfigProvider=newConfigProvider;
    }
