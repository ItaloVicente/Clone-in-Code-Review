	private static boolean containsIgnoreCase(String a
		int alength = a.length();
		int blength = b.length();
		for (int i = 0; i + blength <= alength; i++) {
			if (a.regionMatches(true
				return true;
			}
		}
		return false;
	}

