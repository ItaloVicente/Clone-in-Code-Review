	private List<String> getAllStyles(String id) {
		List<String> m = modifiedStylesheets.get(id);
		if (m != null) {
			m = new ArrayList<>(m);
			m.addAll(globalStyles);
			return m;
		}
