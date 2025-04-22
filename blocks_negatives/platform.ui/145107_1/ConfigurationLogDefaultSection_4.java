	/**
	 * Appends environment variables.
	 */
	private void appendEnvironmentVariables(PrintWriter writer) {
		writer.println();
		writer.println(WorkbenchMessages.SystemSummary_systemVariables);
		TreeMap<String, String> envSorted = new TreeMap<>(System.getenv());
		Set<Entry<String, String>> entrySet = envSorted.entrySet();
		for (Entry<String, String> entry : entrySet) {
			String key = entry.getKey();
			String value = entry.getValue();

			writer.print(key);
			writer.print('=');

				for (int j = 0; j < value.length(); j++) {
					writer.print('*');
				}
				writer.println();
			} else {
				writer.println(value);
			}
		}
	}
