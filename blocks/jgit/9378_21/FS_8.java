		if (factory == null) {
			try {
				Class<?> activatorClass = Class
				System.out.println("Found Java7");
				factory = (FSFactory) activatorClass.newInstance();
			} catch (ClassNotFoundException e) {
				System.out.println("Java7 module not found");
				factory = new FS.FSFactory();
			} catch (UnsupportedClassVersionError e) {
				System.out.println("Java7 module not accessible");
				factory = new FS.FSFactory();
			} catch (Exception e) {
				factory = new FS.FSFactory();
				throw new Error(e);
			}
		}
		return factory.detect(cygwinUsed);
