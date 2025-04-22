		private List<String> getAllStyles(String id) {
			List<String> m = modifiedStylesheets.get(id);
			if (m != null) {
				m = new ArrayList<>(m);
				m.addAll(globalStyles);
				return m;
			}

			List<String> s = stylesheets.get(id);
			if (s == null) {
				s = Collections.emptyList();
			}

			s = new ArrayList<>(s);
			s.addAll(globalStyles);
			return s;
