		}

		public void printTag(String name, Map<String, String> parameters, boolean close) throws IOException {
			printTag(name, parameters, true, true, close);
		}

		private void printTag(String name, Map<String, String> parameters, boolean shouldTab, boolean newLine, boolean close) throws IOException {
			StringBuilder sb = new StringBuilder();
			sb.append('<');
			sb.append(name);
			if (parameters != null) {
