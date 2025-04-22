	private String readUmask() throws Exception {
		Process p = Runtime.getRuntime().exec(
				new String[] { "sh", "-c", "umask" }, null, null);
		final BufferedReader lineRead = new BufferedReader(
				new InputStreamReader(p.getInputStream(), Charset
						.defaultCharset().name()));
		p.waitFor();
		return lineRead.readLine();
