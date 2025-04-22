		final File ptr = new File(getDirectory(),Constants.HEAD);
		final BufferedReader br = new BufferedReader(new FileReader(ptr));
		String ref;
		try {
			ref = br.readLine();
		} finally {
			br.close();
		}
		if (ref.startsWith("ref: "))
			ref = ref.substring(5);
		return ref;
