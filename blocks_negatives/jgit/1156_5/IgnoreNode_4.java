	private void parse(File targetFile) throws IOException {
		if (!targetFile.exists())
			return;

		BufferedReader br = new BufferedReader(new FileReader(targetFile));
		String txt;
		try {
			while ((txt = br.readLine()) != null) {
				txt = txt.trim();
				if (txt.length() > 0 && !txt.startsWith("#"))
					rules.add(new IgnoreRule(txt));
			}
		} finally {
			br.close();
		}
	}
