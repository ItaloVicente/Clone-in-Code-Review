    	}

    	/**
    	 * Write the tag to the stream and format it by indenting it and add new line after the tag
    	 * @param name the name of the tag
    	 * @param parameters map of parameters
    	 * @param close should the tag be ended automatically (=> empty tag)
    	 * @throws IOException
    	 */
    	public void printTag(String name, Map<String, String> parameters, boolean close) throws IOException {
    		printTag(name, parameters, true, true, close);
    	}
