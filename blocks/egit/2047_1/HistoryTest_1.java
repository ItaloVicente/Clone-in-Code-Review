		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(f);
			fileWriter.write(content);
		} finally {
			if (fileWriter != null)
				fileWriter.close();
		}
