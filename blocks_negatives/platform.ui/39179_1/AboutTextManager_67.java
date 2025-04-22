    /**
     * Scan the contents of the about text
     * @param s 
     * @return 
     */
    public static AboutItem scan(String s) {
        ArrayList linkRanges = new ArrayList();
        ArrayList links = new ArrayList();
        
        
		while(urlSeparatorOffset >= 0) {
	
			boolean startDoubleQuote= false;
	
