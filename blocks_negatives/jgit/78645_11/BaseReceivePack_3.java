			final HashSet<String> caps = new HashSet<String>();
			final int nul = line.indexOf('\0');
			if (nul >= 0) {
					caps.add(c);
				this.line = line.substring(0, nul);
			} else
				this.line = line;
			this.capabilities = Collections.unmodifiableSet(caps);
		}

		/** @return non-capabilities part of the line. */
		public String getLine() {
			return line;
		}

		/** @return capabilities parsed from the line. */
		public Set<String> getCapabilities() {
			return capabilities;
