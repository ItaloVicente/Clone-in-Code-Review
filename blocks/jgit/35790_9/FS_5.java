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
			reader = new BufferedReader(new FileReader(file));
			StringBuilder builder = new StringBuilder();
			String line = reader.readLine();
			while (line != null) {
				builder.append(line);
				line = reader.readLine();
				if (line != null)
					builder.append('\n');
			}
			return builder.toString();
		} finally {
			if (reader != null)
				reader.close();
		}
	}

