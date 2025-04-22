		if (factory == null) {
			try {
				Class<?> activatorClass = Class
				factory = (FSFactory) activatorClass.newInstance();
			} catch (ClassNotFoundException e) {
				factory = new FS.FSFactory();
			} catch (UnsupportedClassVersionError e) {
				factory = new FS.FSFactory();
			} catch (Exception e) {
				factory = new FS.FSFactory();
				throw new Error(e);
			}
		}
		return factory.detect(cygwinUsed);
