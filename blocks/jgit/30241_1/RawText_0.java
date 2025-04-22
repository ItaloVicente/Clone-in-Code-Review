		boolean lastNewline = false;
		if (content[end - 1] == '\n') {
			if (ignoreNewline) {
				end--;
			} else {
				lastNewline = true;
			}
		}
