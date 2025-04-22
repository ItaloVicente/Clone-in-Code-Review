		private Map<String
				throws IOException {
			final Map<String
			final BufferedReader br = new BufferedReader(new InputStreamReader(
					in));
			final List<Host> current = new ArrayList<Host>(4);
			String line;

			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (line.length() == 0 || line.startsWith("#"))
					continue;

				final String[] parts = line.split("[ \t]*[= \t]"
				final String keyword = parts[0].trim();
				final String argValue = parts[1].trim();

				if (StringUtils.equalsIgnoreCase("Host"
					current.clear();
					for (final String pattern : argValue.split("[ \t]")) {
						final String name = dequote(pattern);
						Host c = m.get(name);
						if (c == null) {
							c = new Host();
							m.put(name
						}
						current.add(c);
					}
					continue;
				}
