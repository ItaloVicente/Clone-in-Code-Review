		BufferedReader reader = new BufferedReader(new InputStreamReader(
				file.getContents()));
		StringBuilder contentsBuilder = new StringBuilder();
		String line = reader.readLine();
		while (line != null) {
			contentsBuilder.append(line);
			contentsBuilder.append('\n');
			line = reader.readLine();
