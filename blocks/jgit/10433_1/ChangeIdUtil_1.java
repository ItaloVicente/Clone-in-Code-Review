		int indexOfChangeIdLine = lines.length;
		boolean inFooter = false;
		for (int i = lines.length - 1; i > 0; --i) {
			if (!inFooter && lines[i].trim().length() == 0)
				continue;
			inFooter = true;
			if (changeIdPattern.matcher(lines[i].trim()).matches()) {
				indexOfChangeIdLine = i;
				break;
			} else if (lines[i].trim().length() == 0) {
				return -1;
			}
		}
		int indexOfChangeIdLineinString = 0;
		for (int i = 0; i < indexOfChangeIdLine; ++i)
			indexOfChangeIdLineinString += lines[i].length()
					+ delimiter.length();
		return message.indexOf(CHANGE_ID
