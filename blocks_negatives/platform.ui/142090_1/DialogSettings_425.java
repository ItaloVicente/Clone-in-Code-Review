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
