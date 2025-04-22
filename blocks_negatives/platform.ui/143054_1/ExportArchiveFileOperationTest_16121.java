	    	}
	    	else{
	    		File file = new File(filePath);
	    		InputStream in = new FileInputStream(file);
	    		try {
	    			in = new GZIPInputStream(in);
	    			compressed = true;
	    		} catch(IOException e) {
	    			compressed = false;
	    		}
	    		fileName = file.getName();
	    		in.close();
	    	}
    	}
    	catch (IOException e){
    		fail(e.getMessage());
    	}
    	assertTrue(fileName + " does not appear to be compressed.", compressed);
    }

    private void verifyFolders(int folderCount, String type){
    	try{
