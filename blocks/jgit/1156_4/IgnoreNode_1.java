	public void parse(InputStream in) throws IOException {
		BufferedReader br = asReader(in);
		String txt;
		while ((txt = br.readLine()) != null) {
			txt = txt.trim();
			if (txt.length() > 0 && !txt.startsWith("#"))
				rules.add(new IgnoreRule(txt));
