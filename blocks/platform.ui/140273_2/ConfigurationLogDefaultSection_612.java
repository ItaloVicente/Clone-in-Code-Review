		appendFeatures(writer);
		appendRegistry(writer);
		appendUserPreferences(writer);
	}

	private void appendProperties(PrintWriter writer) {
		writer.println();
		writer.println(WorkbenchMessages.SystemSummary_systemProperties);
		Properties properties = System.getProperties();
