	private static class NamesInSection implements SectionParser<Set<String>> {
		private final String section;

		private final String subsection;

		NamesInSection(final String sectionName
			section = sectionName;
			subsection = subSectionName;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + section.hashCode();
			result = prime * result
					+ ((subsection == null) ? 0 : subsection.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			NamesInSection other = (NamesInSection) obj;
			if (!section.equals(other.section))
				return false;
			if (subsection == null) {
				if (other.subsection != null)
					return false;
			} else if (!subsection.equals(other.subsection))
				return false;
			return true;
		}

		public Set<String> parse(Config cfg) {
			final Set<String> result = new HashSet<String>();
			while (cfg != null) {
				for (final Entry e : cfg.state.get().entryList) {
					if (e.name != null
							&& StringUtils.equalsIgnoreCase(e.section
						if (subsection == null && e.subsection == null)
							result.add(StringUtils.toLowerCase(e.name));
						else if (e.subsection != null
								&& e.subsection.equals(subsection))
							result.add(StringUtils.toLowerCase(e.name));

					}
				}
				cfg = cfg.baseConfig;
			}
			return Collections.unmodifiableSet(result);
		}
	}

	private static class SectionNames implements SectionParser<Set<String>> {
		public Set<String> parse(Config cfg) {
			final Set<String> result = new HashSet<String>();
			while (cfg != null) {
				for (final Entry e : cfg.state.get().entryList) {
					if (e.section != null)
						result.add(StringUtils.toLowerCase(e.section));
				}
				cfg = cfg.baseConfig;
			}
			return Collections.unmodifiableSet(result);
		}
	}


