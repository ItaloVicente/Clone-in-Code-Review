			final HashSet<String> caps = new HashSet<>();
			final int nul = line.indexOf('\0');
			if (nul >= 0) {
					caps.add(c);
				this.line = line.substring(0, nul);
			} else
				this.line = line;
			this.capabilities = Collections.unmodifiableSet(caps);
