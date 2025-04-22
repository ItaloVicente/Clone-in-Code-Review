		try {
			Matcher matcher = GITHUB_PR_URL_PATTERN.matcher(input);
			if (matcher.matches()) {
				return new GithubChange(Long.parseLong(matcher.group(2)));
			}
			matcher = GITHUB_PR_INPUT_PATTERN.matcher(input);
			if (matcher.matches()) {
				return new GithubChange(Long.parseLong(matcher.group(1)));
			}
			matcher = DIGITS.matcher(input);
			if (matcher.matches()) {
				return new GithubChange(Long.parseLong(input));
			}
		} catch (NumberFormatException e) {
		}
		return null;
