		private final List<String> patterns;

		HostEntry() {
			this.patterns = Collections.emptyList();
		}

		HostEntry(List<String> patterns) {
			this.patterns = patterns;
		}

		boolean matches(String hostName) {
			boolean doesMatch = false;
			for (String pattern : patterns) {
					if (patternMatchesHost(pattern.substring(1)
						return false;
					}
				} else if (!doesMatch
						&& patternMatchesHost(pattern
					doesMatch = true;
				}
			}
			return doesMatch;
		}

