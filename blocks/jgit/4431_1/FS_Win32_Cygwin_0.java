		String useCygPath = System.getProperty("jgit.usecygpath");
		if (useCygPath != null && useCygPath.equals("true")) {
			String w = readPipe(dir
					new String[] { cygpath
					"UTF-8");
			if (w != null)
				return new File(w);
		}
