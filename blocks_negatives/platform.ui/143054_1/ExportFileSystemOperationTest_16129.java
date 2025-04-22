    	File[] files = directory.listFiles();
    	if (files != null){
	    	for (File file : files) {
	    		assertTrue("Export failed to export file: " + file.getName(), file.exists());
	    	}
    	}
