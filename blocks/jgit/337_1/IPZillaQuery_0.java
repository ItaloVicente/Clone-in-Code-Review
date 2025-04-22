
		String content = readFully(c);
		Matcher matcher = Pattern.compile("<title>(.*)</title>"
				Pattern.CASE_INSENSITIVE).matcher(content);
		if (!matcher.find()) {
			throw new IOException("Login as " + username + " to " + login
					+ " failed: Response not HTML as expected");
		}

		String title = matcher.group(1);
		if (!"IPZilla Main Page".equals(title)) {
			throw new IOException("Login as " + username + " to " + login
					+ " failed; page title was \"" + title + "\"");
		}
	}

	private String readFully(HttpURLConnection c) throws IOException {
		String enc = c.getContentEncoding();
		Reader reader;
		if (enc != null) {
			reader = new InputStreamReader(c.getInputStream()
		} else {
			reader = new InputStreamReader(c.getInputStream()
		}
		try {
			StringBuilder b = new StringBuilder();
			BufferedReader r = new BufferedReader(reader);
			String line;
			while ((line = r.readLine()) != null) {
				b.append(line).append('\n');
			}
			return b.toString();
		} finally {
			reader.close();
		}
