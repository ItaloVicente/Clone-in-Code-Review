		private String escapeRegexCharacters(String inputString) {
			final List<Character> metaCharacters = Arrays.asList( '\\', '^', '$', '{', '}', '[', ']', '(', ')', '+', '|', '<', '>', '-','&' );
			StringBuilder output = new StringBuilder();
			for(char character : inputString.toCharArray()) {
				if (metaCharacters.contains(character)) {
					output.append('\\');
				}
				output.append(character);
			}
			return output.toString();
		}

