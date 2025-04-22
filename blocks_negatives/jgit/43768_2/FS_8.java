			try {
				Class<?> activatorClass = Class
				factory = (FSFactory) activatorClass.newInstance();
			} catch (ClassNotFoundException e) {
				factory = new FS.FSFactory();
			} catch (UnsupportedClassVersionError e) {
				factory = new FS.FSFactory();
			} catch (InstantiationException e) {
				factory = new FS.FSFactory();
			} catch (IllegalAccessException e) {
				factory = new FS.FSFactory();
			}
