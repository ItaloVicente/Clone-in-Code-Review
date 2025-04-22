		if (pattern.length() > 0 && pattern.charAt(pattern.length() - 1) == '\\') {
			int backslashesCount = 1;
			for (int i = pattern.length() - 2; i >= 0; i--) {
				if (pattern.charAt(i) == '\\') {
					backslashesCount++;			    
				} else {
					break;
				}
			}
			if (backslashesCount % 2 == 1) {
				this.matcher = NO_MATCH;
				dirOnly = false;
				return;
			}
		}
