		if (tmp - pathOffset == 4
				|| tmp2 - pathOffset == 4
				&& SystemReader.getInstance().getProperty("os.name")
						.equals("Windows")) {
			if (path[pathOffset] == '.'
					&& (path[pathOffset + 1] == 'g' || path[pathOffset + 1] == 'G')
					&& (path[pathOffset + 2] == 'i' || path[pathOffset + 2] == 'I')
					&& (path[pathOffset + 3] == 't' || path[pathOffset + 3] == 'T'))
				invalid = true;
		}
		if (maybeInvalid
				&& SystemReader.getInstance().getProperty("os.name")
						.equals("Windows"))
			invalid = true;
		else if (tmp - pathOffset == 0)
			invalid = true;
		else if (tmp - pathOffset == 2
				|| tmp2 - pathOffset == 2
				&& SystemReader.getInstance().getProperty("os.name")
						.equals("Windows")) {
			if (path[pathOffset] == '.' && path[pathOffset + 1] == '.')
				invalid = true;
		} else if (tmp - pathOffset == 1
				|| tmp2 - pathOffset == 1
				&& SystemReader.getInstance().getProperty("os.name")
						.equals("Windows")) {
			if (path[pathOffset] == '.')
				invalid = true;
		}
