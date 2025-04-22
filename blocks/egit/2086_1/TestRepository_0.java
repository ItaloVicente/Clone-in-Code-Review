		FileWriter fw = null;
		try {
			fw = new FileWriter(file, append);
			fw.append(content);
		} finally {
			if (fw != null)
				fw.close();
		}
