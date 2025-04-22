		assertIteration(F, "src/config/readme.txt", asList(DELTA_UNSET), null,
				asList(CUSTOM_VALUE));
		assertIteration(F, "src/config/windows.file", null, asList(EOL_CRLF),
				null);
		assertIteration(F, "src/config/windows.txt", asList(DELTA_UNSET),
				asList(EOL_CRLF), asList(CUSTOM_VALUE));
