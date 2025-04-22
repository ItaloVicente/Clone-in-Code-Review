    ConfigurationProvider newConfigProvider;
    Resubscriber(ConfigurationProvider newConfigProvider){
      this.newConfigProvider=newConfigProvider;
    }

    @Override
	public void run() {
      String threadNameBase = "Couchbase/Resubscriber (Status: ";
      Thread.currentThread().setName(threadNameBase + "running)");
