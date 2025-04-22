		String umask = readUmask();
		assumeNotNull(umask);

		char others = umask.charAt(umask.length() - 1);

		boolean badUmask;
		if (others != '0' && others != '2' && others != '4' && others != '6') {
			badUmask = true;
		} else {
			badUmask = false;
		}

