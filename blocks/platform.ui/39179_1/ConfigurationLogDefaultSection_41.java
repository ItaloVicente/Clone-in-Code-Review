		appendProperties(writer);
		appendFeatures(writer);
		appendRegistry(writer);
		appendUserPreferences(writer);
	}

	private void appendProperties(PrintWriter writer) {
		writer.println();
		writer.println(WorkbenchMessages.SystemSummary_systemProperties);
		Properties properties = System.getProperties();
		SortedSet<Object> set = new TreeSet<Object>(new Comparator<Object>() {
			@Override
