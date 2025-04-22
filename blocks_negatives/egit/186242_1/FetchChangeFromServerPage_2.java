		return new ChangeList(repo, uri);
	}

	static Change fromRef(String refName) {
		try {
			if (refName == null) {
				return null;
			}
			Matcher m = GITHUB_PR_REF_PATTERN.matcher(refName);
			if (!m.matches() || m.group(1) == null) {
				return null;
			}
			return new GithubChange(Long.parseLong(m.group(1)));
		} catch (NumberFormatException | IndexOutOfBoundsException e) {
			return null;
		}
