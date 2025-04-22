		try {
			Matcher matcher = GERRIT_URL_PATTERN.matcher(input);
			if (matcher.matches()) {
				String first = matcher.group(1);
				String second = matcher.group(2);
				String third = matcher.group(3);
				if (second != null && !second.isEmpty()) {
					if (third != null && !third.isEmpty()) {
						return Change.create(Integer.parseInt(second),
								Integer.parseInt(third));
					} else if (input.startsWith("http")) { //$NON-NLS-1$
						return Change.create(Integer.parseInt(first),
								Integer.parseInt(second));
					} else {
						int firstNum = Integer.parseInt(first);
						int secondNum = Integer.parseInt(second);
						if (firstNum > secondNum) {
							return Change.create(firstNum, secondNum);
