	
	static {
		String viewmode = null;
		boolean propsFileExists;
		try {
			Properties properties = new Properties();
		    properties.load(new FileInputStream("config.properties"));
		    viewmode = properties.getProperty("viewmode");
		    propsFileExists = true;
		} catch (IOException e) {
			propsFileExists = false;
		}
		if (!propsFileExists) {
			MODE_ERROR = "Can't find config.properties. Setting viewmode " +
					"to development mode";
			MODE_PREFIX = DEV_PREFIX;
		} else if (viewmode == null) {
			MODE_ERROR = "viewmode doesn't exist in config.properties. " +
	    			"Setting viewmode to development mode";
	    	MODE_PREFIX = DEV_PREFIX;
	    } else if (viewmode.equals(MODE_PRODUCTION)) {
	    	MODE_ERROR = "viewmode set to production mode";
	    	MODE_PREFIX = PROD_PREFIX;
	    } else if (viewmode.equals(MODE_DEVELOPMENT)){
	    	MODE_ERROR = "viewmode set to development mode";
	    	MODE_PREFIX = DEV_PREFIX;
	    } else {
	    	MODE_ERROR = "unknown value \"" + viewmode + "\" for property viewmode";
	    	MODE_PREFIX = DEV_PREFIX;
	    }
	}
