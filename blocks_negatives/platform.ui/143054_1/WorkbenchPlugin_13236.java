	 * Check to see if the command line parameter for -nl
	 * has been set. If so imply the orientation from this
	 * specified Locale. If it is a bidirectional Locale
	 * return SWT#RIGHT_TO_LEFT.
	 * If it has not been set or has been set to
	 * a unidirectional Locale then return SWT#NONE.
