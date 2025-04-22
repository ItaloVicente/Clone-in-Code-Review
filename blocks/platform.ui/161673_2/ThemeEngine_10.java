		private void registerStyle(String id, String stylesheet) {
			List<String> s = stylesheets.get(id);
			if (s == null) {
				s = new ArrayList<>();
				stylesheets.put(id, s);
			}
			s.add(stylesheet);
