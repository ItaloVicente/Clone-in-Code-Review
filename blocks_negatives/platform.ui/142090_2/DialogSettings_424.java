    	}

    	/**
    	 * Create a new XMLWriter
    	 * @param output the write to used when writing to
    	 * @throws IOException
    	 */
    	public XMLWriter(Writer output) throws IOException {
    		super(output);
    		tab = 0;
    		writeln(XML_VERSION);
    	}
