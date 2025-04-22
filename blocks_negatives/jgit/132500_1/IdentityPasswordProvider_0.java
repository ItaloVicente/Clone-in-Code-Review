	public String getPassword(String resourceKey) throws IOException {
		char[] pass = getPassword(resourceKey,
				current.computeIfAbsent(resourceKey, r -> new State()));
		if (pass == null) {
			return null;
		}
		try {
			return new String(pass);
		} finally {
			Arrays.fill(pass, '\000');
		}
