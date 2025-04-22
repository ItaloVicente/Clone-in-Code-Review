        	Properties props = new Properties();
        	FileInputStream is = new FileInputStream(versionFile);
        	try {
        		props.load(is);
        	} finally {
        		is.close();
        	}
