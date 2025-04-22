	public static class FirstLine {
		private final String line;
		private final Set<String> options;

		public FirstLine(String line) {
			if (line.length() > 45) {
				final HashSet<String> opts = new HashSet<String>();
				String opt = line.substring(45);
				if (opt.startsWith(" "))
					opt = opt.substring(1);
				for (String c : opt.split(" "))
					opts.add(c);
				this.line = line.substring(0
				this.options = Collections.unmodifiableSet(opts);
			} else {
				this.line = line;
				this.options = Collections.emptySet();
			}
		}

		public String getLine() {
			return line;
		}

		public Set<String> getOptions() {
			return options;
		}
	}

