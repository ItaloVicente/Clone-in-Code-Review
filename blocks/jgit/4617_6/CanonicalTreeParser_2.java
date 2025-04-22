
	@Override
	public boolean isValidPath() {
		if (parent != null)
			if (!parent.isValidPath())
				return false;

		boolean isWindows = SystemReader.getInstance().getProperty("os.name")
				.equals("Windows");
		boolean isOSX = SystemReader.getInstance().getProperty("os.name")
				.equals("Mac OS X");
		boolean ignCase = isOSX || isWindows;

		int ptr = currPtr;
		while (raw[ptr] != ' ')
			ptr++;

		int start = ptr + 1;
		while (raw[ptr] != 0) {
			if (raw[ptr] == '/')
				return false;
			if (isWindows) {
				if (raw[ptr] == '\\')
					return false;
				if (raw[ptr] == ':')
					return false;
			}
			ptr++;
		}
		if (ptr - start == 1) {
			if (raw[start] == '.')
				return false;
		} else if (ptr - start == 2) {
			if (raw[start] == '.')
				if (raw[start + 1] == '.')
					return false;
		} else if (ptr - start == 4) {
			if (raw[start] == '.')
				if (raw[start + 1] == 'g' || (ignCase && raw[start + 1] == 'G'))
					if (raw[start + 2] == 'i'
							|| (ignCase && raw[start + 2] == 'I'))
						if (raw[start + 3] == 't'
								|| (ignCase && raw[start + 3] == 'T'))
							return false;
		} else if (isWindows) {
			for (int i = ptr - 1; i >= start; --i)
				if (raw[i] == '.' || raw[i] == ' ')
					return false;
		}

		return true;
	}
