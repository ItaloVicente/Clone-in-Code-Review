    	}

    	/**
    	 * start the tag
    	 * @param name the name of the tag
    	 * @param parameters map of parameters
    	 * @throws IOException
    	 */
    	public void startTag(String name, HashMap<String, String> parameters) throws IOException {
    		startTag(name, parameters, true);
    		tab++;
    	}

    	private void startTag(String name, HashMap<String, String> parameters, boolean newLine) throws IOException {
    		printTag(name, parameters, true, newLine, false);
    	}

    	private static void appendEscapedChar(StringBuilder buffer, char c) {
    		String replacement = getReplacement(c);
    		if (replacement != null) {
    			buffer.append('&');
    			buffer.append(replacement);
    			buffer.append(';');
    		} else {
    			buffer.append(c);
    		}
    	}

    	private static String getEscaped(String s) {
    		StringBuilder result = new StringBuilder(s.length() + 10);
    		for (int i = 0; i < s.length(); ++i) {
