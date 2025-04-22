	
	public static String nullIfEmpty(String str) {
		return str.trim().length() == 0? null: str;
	}
	
	public static String emptyIfNull(String str) {
		return str == null? EMPTY_STRING: str;
	}
