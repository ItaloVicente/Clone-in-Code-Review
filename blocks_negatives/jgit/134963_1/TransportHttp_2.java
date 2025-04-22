
		if (this.headers != null && !this.headers.isEmpty()) {
			for (Map.Entry<String, String> entry : this.headers.entrySet())
				conn.setRequestProperty(entry.getKey(), entry.getValue());
		}
		authMethod.configureRequest(conn);
		return conn;
