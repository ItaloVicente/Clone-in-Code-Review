	private static class CaseFoldingSet extends AbstractSet<String> {
		private final Map<String

		CaseFoldingSet(Map<String
			this.names = Collections.unmodifiableMap(names);
		}

		@Override
		public boolean contains(Object needle) {
			if (!(needle instanceof String))
				return false;

			String n = (String) needle;
			return names.containsKey(n)
					|| names.containsKey(StringUtils.toLowerCase(n));
		}

		@Override
		public Iterator<String> iterator() {
			return names.values().iterator();
		}

		@Override
		public int size() {
			return names.size();
		}
	}
