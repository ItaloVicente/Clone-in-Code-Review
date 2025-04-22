	public static boolean testWildcard(String pattern, String str) {
		if (pattern.equals("*")) {//$NON-NLS-1$
			return true;
		} else if (pattern.startsWith("*")) {//$NON-NLS-1$
			if (pattern.endsWith("*")) {//$NON-NLS-1$
				if (pattern.length() == 2) {
