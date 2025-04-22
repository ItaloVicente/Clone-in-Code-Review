		try {
			final File ptr = new File(getDirectory(), Constants.HEAD);
			final BufferedReader br = new BufferedReader(new FileReader(ptr));
			String ref;
			try {
				ref = br.readLine();
			} finally {
				br.close();
			}
			if (ref.startsWith("ref: "))
				ref = ref.substring(5);
			if (ref.startsWith("refs/heads/"))
				ref = ref.substring(11);
			return ref;
		} catch (FileNotFoundException e) {
			final File ptr = new File(getDirectory(),head-name);
			final BufferedReader br = new BufferedReader(new FileReader(ptr));
			String ref;
			try {
				ref = br.readLine();
			} finally {
				br.close();
			}
			return ref;
		}
