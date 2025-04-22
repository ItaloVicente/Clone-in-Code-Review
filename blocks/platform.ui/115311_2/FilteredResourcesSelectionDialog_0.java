			if (matching.length() == 0 || string.length() == 0) {
				return positions;
			}

			char lastChar = matching.charAt(matching.length() - 1);
			if (lastChar == ' ') {
				matching = matching.substring(0, matching.length() - 1);
			} else if (lastChar == '<') {
				matching = matching.substring(0, matching.length() - 2);
			}
