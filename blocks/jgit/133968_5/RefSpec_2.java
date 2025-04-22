	private static boolean destinationContains(List<String> thisDst
		if (thisWildcard == -1) {
			return thisDst.equals(thatDst);
		}
		List<String> thisPrefix = thisDst.subList(0
		List<String> thisSuffix = thisDst.subList(thisWildcard + 1
				thisDst.size());
		return startsWith(thatDst
	}

	private static boolean startsWith(List<String> list
			List<String> maybeStartsWith) {
		if (maybeStartsWith.size() > list.size()) {
			return false;
		}
		for (int i = 0; i < maybeStartsWith.size(); i++) {
			if (!maybeStartsWith.get(i).equals(list.get(i))) {
				return false;
			}
		}
		return true;
	}

	private static boolean endsWith(List<String> list
			List<String> maybeEndsWith) {
		if (maybeEndsWith.size() > list.size()) {
			return false;
		}
		int suffixSize = maybeEndsWith.size();
		int listSize = list.size();
		for (int i = 1; i <= maybeEndsWith.size(); i++) {
			if (!maybeEndsWith.get(suffixSize - i)
					.equals(list.get(listSize - i))) {
				return false;
			}
		}
		return true;
	}

