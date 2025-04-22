
		Collection<String> extraParameters = null;

		int nulnul = cmd.indexOf("\0\0");
		if (nulnul != -1) {
			extraParameters = Arrays.asList(cmd.substring(nulnul + 2).split("\0"));
		}

