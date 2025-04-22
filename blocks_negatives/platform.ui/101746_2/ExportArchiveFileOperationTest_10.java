	    		ZipFile zipFile = new ZipFile(filePath);
	    		Enumeration entries = zipFile.entries();
	    		while (entries.hasMoreElements()){
	    			ZipEntry entry = (ZipEntry)entries.nextElement();
	    			allEntries.add(entry.getName());
	    		}
	    		zipFile.close();
