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

    	private  void writeln(String text) throws IOException {
    		write(text);
    		newLine();
    	}

    	/**
    	 * write the intended end tag
    	 * @param name the name of the tag to end
    	 * @throws IOException
    	 */
    	public void endTag(String name) throws IOException {
    		tab--;
    		printTag("/" + name, null, false); //$NON-NLS-1$
    	}

    	private void printTabulation() throws IOException {
    		for (int i = 0; i < tab; i++) {
