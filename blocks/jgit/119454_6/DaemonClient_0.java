
		boolean protocolV2Requested = false;

		int nulnul = cmd.indexOf("\0\0");
		if (nulnul != -1) {
			String[] extraParameters = cmd.substring(nulnul + 2).split("\0");
			protocolV2Requested = GitProtocolHelpers.supportsVersion(
				Arrays.asList(extraParameters)
		}

