        super.doTearDown();
        File root = new File(localDirectory);
        if (root.exists()){
        	File[] files = root.listFiles();
        	if (files != null){
        		for (int i = 0; i < files.length; i++) {
					if (!files[i].delete()) {
						fail("Could not delete " + files[i].getAbsolutePath());
