				ZipFile zipFile = new ZipFile(filePath);
				fileName = zipFile.getName();
	    		Enumeration entries = zipFile.entries();
	    		while (entries.hasMoreElements()){
	    			ZipEntry entry = (ZipEntry)entries.nextElement();
	    			compressed = entry.getMethod() == ZipEntry.DEFLATED;
	    		}
	    		zipFile.close();
