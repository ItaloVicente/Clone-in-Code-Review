	/** Data in the first line of a request, the line itself plus options. */
	public static class FirstLine {
		private final String line;
		private final Set<String> options;

		/**
		 * Parse the first line of a receive-pack request.
		 *
		 * @param line
		 *            line from the client.
		 */
		public FirstLine(String line) {
			if (line.length() > 45) {
				final HashSet<String> opts = new HashSet<>();
				String opt = line.substring(45);
					opt = opt.substring(1);
					opts.add(c);
				this.line = line.substring(0, 45);
				this.options = Collections.unmodifiableSet(opts);
			} else {
				this.line = line;
				this.options = Collections.emptySet();
			}
		}

		/** @return non-capabilities part of the line. */
		public String getLine() {
			return line;
		}

		/** @return options parsed from the line. */
		public Set<String> getOptions() {
			return options;
		}
	}

