		File dotmodules = new File(localDb.getWorkTree(),
				Constants.DOT_GIT_MODULES);
		localDb.close();
		BufferedReader reader = new BufferedReader(new FileReader(dotmodules));
		boolean foo = false;
		boolean bar = false;
		while (true) {
			String line = reader.readLine();
			if (line == null)
				break;
			if (line.contains("submodule \"foo\""))
				foo = true;
			if (line.contains("submodule \"bar\""))
				bar = true;
