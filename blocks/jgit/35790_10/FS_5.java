	public void writeToFile(String msg
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(file));
			writer.write(msg);
		} finally {
			if (writer != null)
				writer.close();
		}
	}

	public String readFileContent(File file) throws IOException {
		BufferedReader reader = null;
		try {
			StringBuilder builder = new StringBuilder();
			reader = new BufferedReader(new FileReader(file));
			char[] buf = new char[1024];
			int numRead = 0;
			while ((numRead = reader.read(buf)) != -1) {
				String readData = String.valueOf(buf
				builder.append(readData);
			}
			return builder.toString();
		} finally {
			if (reader != null)
				reader.close();
		}
	}

