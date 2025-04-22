		try (FileOutputStream fos = new FileOutputStream(dest); FileInputStream fis = new FileInputStream(src)) {
	    	byte[] buffer = new byte[1024];
	    	int length;
	    	while ((length = fis.read(buffer)) > 0) {
	    		fos.write(buffer, 0, length);
	    	}
	    }
