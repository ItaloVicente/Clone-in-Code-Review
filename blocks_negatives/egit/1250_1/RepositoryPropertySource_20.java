		if (tok.countTokens() == 3) {
			if (value == null || value.length() == 0) {
				configuration.unset(tok.nextToken(), tok.nextToken(), tok
						.nextToken());
			} else {
				configuration.setString(tok.nextToken(), tok.nextToken(), tok
						.nextToken(), value);
			}
