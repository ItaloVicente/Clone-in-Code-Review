		int footerFirstLine = indexOfFirstFooterLine(lines);
		if (footerFirstLine == lines.length)
			return -1;

		int indexOfFooter = 0;
		for (int i = 0; i < footerFirstLine; ++i)
			indexOfFooter += lines[i].length() + delimiter.length();
		return message.indexOf(CHANGE_ID, indexOfFooter);
