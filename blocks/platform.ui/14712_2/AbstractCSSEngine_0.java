		    InputStream stream = null;
		    try {
		    	stream = url.openStream();
				InputSource tempStream = new InputSource();
				tempStream.setURI(url.toString());
				tempStream.setByteStream(stream);
				styleSheet = (CSSStyleSheet) this.parseStyleSheet(tempStream, true);
				stream.close();
		    } catch (IOException ex) {
		    	if (stream != null) {
		    		stream.close();
		    	}
		    }
