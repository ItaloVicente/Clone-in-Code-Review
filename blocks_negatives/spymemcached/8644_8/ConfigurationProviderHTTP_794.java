    /**
     * Helper method that reads content from url connection to the string
     * @param connection a given url connection
     * @return content string
     * @throws IOException
     */
    private String readToString(URLConnection connection) throws IOException {
	BufferedReader reader = null;
	try {
		InputStream inStream = connection.getInputStream();
		if (connection instanceof java.net.HttpURLConnection) {
			HttpURLConnection httpConnection = (HttpURLConnection) connection;
			if (httpConnection.getResponseCode() == 403) {
				throw new IOException("Service does not accept the authentication credentials: "
					+ httpConnection.getResponseCode() + httpConnection.getResponseMessage());
			} else if (httpConnection.getResponseCode() >= 400) {
				throw new IOException("Service responded with a failure code: "
					+ httpConnection.getResponseCode() + httpConnection.getResponseMessage());
			}
		} else {
			throw new IOException("Unexpected URI type encountered");
		}
		reader = new BufferedReader(new InputStreamReader(inStream));
		String str;
		StringBuilder buffer = new StringBuilder();
		while ((str = reader.readLine()) != null) {
			buffer.append(str);
		}
		return buffer.toString();
	    } finally {
	    	if (reader != null) {
	    		reader.close();
	    	}
	}
