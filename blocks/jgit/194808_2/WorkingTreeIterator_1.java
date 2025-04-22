		if (iMode == FileMode.SYMLINK
				&& getOptions().getSymLinks() == SymLinks.FALSE
				&& (wtMode == FileMode.REGULAR_FILE
						|| wtMode == FileMode.EXECUTABLE_FILE)) {
			return iMode;
		}
