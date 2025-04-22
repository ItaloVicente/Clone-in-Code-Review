		ix = s.indexOf('\n', start);
		while (ix != -1) {
			start = end + 1;
			end = ix = s.indexOf('\n', start);
			lineno++;
			if (ix != -1) {
				while (s.charAt(start) == ' ' || s.charAt(start) == '\t') {
					start++;
				}
				if (Character.isDigit(s.charAt(start))) {
					if (lastme != null) {
						lastme.setNumberOfLines(lineno - lastlineno - 1);
					}
					lastlineno = lineno;
					String markName = parseHeading(s, start, end);
