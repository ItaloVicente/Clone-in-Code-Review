	public static class FirstLine {
		private final String line;
		private final Set<String> capabilities;

		public FirstLine(String line) {
			final HashSet<String> caps = new HashSet<String>();
			final int nul = line.indexOf('\0');
			if (nul >= 0) {
				for (String c : line.substring(nul + 1).split(" "))
					caps.add(c);
			}
			this.line = line.substring(0
			this.capabilities = Collections.unmodifiableSet(caps);
		}

		public String getLine() {
			return line;
		}

		public Set<String> getCapabilities() {
			return capabilities;
		}
	}

