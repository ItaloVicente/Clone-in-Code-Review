	private boolean canApplyAt(List<String> hunkLines
			int line) {
		int sz = hunkLines.size();
		int limit = newLines.size();
		int pos = line;
		for (int j = 1; j < sz; j++) {
			String hunkLine = hunkLines.get(j);
			switch (hunkLine.charAt(0)) {
			case ' ':
			case '-':
				if (pos >= limit
						|| !newLines.get(pos).equals(hunkLine.substring(1))) {
					return false;
				}
				pos++;
				break;
			default:
				break;
			}
		}
		return true;
	}

