		appendFeatures(writer);
		appendRegistry(writer);
		appendUserPreferences(writer);
	}

	private void appendProperties(PrintWriter writer) {
		writer.println();
		writer.println(WorkbenchMessages.SystemSummary_systemProperties);
		Properties properties = System.getProperties();
		SortedSet<Object> set = new TreeSet<>((o1, o2) -> {
			String s1 = (String) o1;
			String s2 = (String) o2;
			return s1.compareTo(s2);
