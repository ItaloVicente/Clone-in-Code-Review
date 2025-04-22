	private static boolean isAllHex(String str
		while (ptr < str.length()) {
			if (!isHex(str.charAt(ptr++)))
				return false;
		}
		return true;
	}

