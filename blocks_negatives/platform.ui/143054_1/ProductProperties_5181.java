        String[] tempMappings = getMappings(product.getDefiningBundle());
                /*
    	 * Check if the mapping value is a system property, specified
    	 * by '$' at the beginning and end of the string.  If so, update
    	 * the mappings array with the system property value.
    	 */
        for (int i=0; i<tempMappings.length; i++) {
        	String nextString = tempMappings[i];
        	int length = nextString.length();

        	if (length > 2 && nextString.charAt(0) == '$' && nextString.charAt(length-1) == '$') {
        		String systemPropertyKey = nextString.substring(1, length-1);
        		tempMappings[i] = System.getProperty(systemPropertyKey, ""); //$NON-NLS-1$;
        	}
        }
