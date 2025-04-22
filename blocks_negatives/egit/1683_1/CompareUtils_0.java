	/**
	 * @param ci
	 * @return a truncated revision identifier if it is long
	 */
	public static String truncatedRevision(String ci) {
		if(ci.length() > 10)
			return ci.substring(0, 7) + "..."; //$NON-NLS-1$
		else
			return ci;
	}


