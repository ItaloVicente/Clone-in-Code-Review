
		boolean protocolV2Requested = false;

		int nulnul = cmd.indexOf("\0\0");
		if (nulnul != -1) {
			String extraParameters = cmd.substring(nulnul + 2);
			protocolV2Requested = ("\0" + extraParameters + "\0").contains(
					"\0version=2\0");
		}

