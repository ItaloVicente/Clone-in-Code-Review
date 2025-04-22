	private static void touch(File f, String contents) throws Exception {
		FileWriter w = new FileWriter(f);
		w.write(contents);
		w.close();
	}

