
	private String getStackTrace(Exception actualException) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		actualException.printStackTrace(pw);
		return sw.toString();
	}

	private void writeLfsConfig() throws IOException {
		writeLfsConfig(LFS_SERVER_URL1
	}

	private void writeLfsConfig(String lfsUrl
			throws IOException {
		writeLfsConfig(lfsUrl
	}

	private void writeLfsConfig(String lfsUrl
			String subsection
		StringBuilder config = new StringBuilder();
		config.append("[");
		config.append(section);
		if (subsection != null) {
			config.append(" \"");
			config.append(subsection);
			config.append("\"");
		}
		config.append("]\n");
		config.append("    ");
		config.append(name);
		config.append(" = ");
		config.append(lfsUrl);
		writeTrashFile(Constants.DOT_LFS_CONFIG
	}

	private void writeInvalidLfsConfig() throws IOException {
		writeTrashFile(Constants.DOT_LFS_CONFIG
				"{lfs]\n    url = " + LFS_SERVER_URL1);
	}

	private void checkLfsUrl(String lfsUrl) throws IOException {
		HttpConnection lfsServerConn;
		lfsServerConn = LfsConnectionFactory.getLfsConnection(db
				HttpSupport.METHOD_POST

		assertEquals(lfsUrl + Protocol.OBJECTS_LFS_ENDPOINT
				lfsServerConn.getURL().toString());
	}
