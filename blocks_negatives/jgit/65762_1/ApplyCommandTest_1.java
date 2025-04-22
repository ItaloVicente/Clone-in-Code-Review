
		if (postExists)
			b = new RawText(readFile(name + "_PostImage"));

		return git
				.apply()
				.setPatch(getTestResource(name + ".patch")).call();
