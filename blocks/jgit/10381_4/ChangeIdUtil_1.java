		int indexOfChangeIdLine = 0;
		boolean inFooter = false;
		for (int i = lines.length - 1; i >= 0; --i) {
			if (!inFooter && isEmptyLine(lines[i]))
				continue;
			inFooter = true;
			if (changeIdPattern.matcher(lines[i].trim()).matches()) {
				indexOfChangeIdLine = i;
				break;
			} else if (isEmptyLine(lines[i]) || i == 0)
				return -1;
		}
		int indexOfChangeIdLineinString = 0;
		for (int i = 0; i < indexOfChangeIdLine; ++i)
			indexOfChangeIdLineinString += lines[i].length()
					+ delimiter.length();
		return indexOfChangeIdLineinString;
	}
