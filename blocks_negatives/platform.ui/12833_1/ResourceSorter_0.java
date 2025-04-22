	/*
	 * Note that this class is required to be used by the Common Navigator
	 * since ViewerSorter is part of its API. But the ViewerSorter uses the
	 * undesireable Java Collator (rather than the ICU one), we we have to put
	 * this here. 
	 */
	private Collator icuCollator;
	
