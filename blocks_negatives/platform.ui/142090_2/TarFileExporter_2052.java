    		gzipOutputStream = new GZIPOutputStream(new FileOutputStream(filename));
    		outputStream = new TarOutputStream(new BufferedOutputStream(gzipOutputStream));
    	} else {
    		outputStream = new TarOutputStream(new BufferedOutputStream(new FileOutputStream(filename)));
    	}
    }
