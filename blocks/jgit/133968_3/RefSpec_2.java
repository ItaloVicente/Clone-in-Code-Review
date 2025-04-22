	private static boolean destinationContains(List<String> thisDst
		if (thisWildcard == -1) {
			return thisDst.equals(thatDst);
		}
		if (thisDst.size() > thatDst.size()) {
			return false;
		}
		List<String> thisPrefix = thisDst.subList(0
		List<String> thisSuffix = thisDst.subList(thisWildcard + 1
				thisDst.size());
		return hasPrefix(thatDst
	}

	private static boolean hasPrefix(List<?> list
		if (prefix.size() > list.size()) {
			return false;
		}
		for (int i = 0; i < prefix.size(); i++) {
			if (!prefix.get(i).equals(list.get(i))) {
				return false;
			}
		}
		return true;
	}

	private static boolean hasSuffix(List<?> list
		if (suffix.size() > list.size()) {
			return false;
		}
		int suffixSize = suffix.size();
		int listSize = list.size();
		for (int i = 1; i <= suffix.size(); i++) {
			if (!suffix.get(suffixSize - i).equals(list.get(listSize - 1))) {
				return false;
			}
		}
		return true;
	}

