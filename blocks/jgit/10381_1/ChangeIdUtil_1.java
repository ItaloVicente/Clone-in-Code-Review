		int indexOfChangeIdLine = lines.length;
		for (int i = lines.length - 1; i > 0; --i) {
			if (changeIdPattern.matcher(lines[i].trim()).matches()) {
				indexOfChangeIdLine = i;
				break;
			}
		}
		int indexOfChangeIdLineinString = 0;
		for (int i = 0; i < indexOfChangeIdLine; ++i)
			indexOfChangeIdLineinString += lines[i].length()
					+ delimiter.length();
		return message.indexOf(CHANGE_ID
