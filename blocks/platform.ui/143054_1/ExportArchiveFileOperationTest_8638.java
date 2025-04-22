		super.doTearDown();
		File root = new File(localDirectory);
		if (root.exists()){
			File[] files = root.listFiles();
			if (files != null){
				for (File file : files) {
					if (!file.delete()) {
						fail("Could not delete " + file.getAbsolutePath());
