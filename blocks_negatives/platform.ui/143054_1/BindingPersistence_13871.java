			StringTokenizer tokenizer = new StringTokenizer(commaSeparatedString, ", "); //$NON-NLS-1$
			int count = tokenizer.countTokens();
			String[] tokens = new String[count];
			for (int i = 0; i < tokens.length; i++) {
				tokens[i] = tokenizer.nextToken();
			}
			return tokens;
