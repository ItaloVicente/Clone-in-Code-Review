		Matcher matcher = GERRIT_URL_PATTERN.matcher(input);
		if (matcher.matches()) {
			String first = matcher.group(1);
			String second = matcher.group(2);
			String third = matcher.group(3);
			if (second != null && !second.isEmpty()) {
				if (third != null && !third.isEmpty()) {
					return second;
					return first;
				} else {
					try {
						if (Integer.parseInt(first) > Integer
								.parseInt(second)) {
							return first;
