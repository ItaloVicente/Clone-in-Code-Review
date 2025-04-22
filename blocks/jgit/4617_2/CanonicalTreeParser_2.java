			switch (c) {
			case 0:
				break F;
			case '/':
				invalid = true;
				break;
			case ' ':
			case '.':
				maybeInvalid = true;
				break;
			case '\\':
			case ':':
				if (SystemReader.getInstance().getProperty("os.name").equals("Windows"))
					invalid = true;
				maybeInvalid = false;
				tmp2 = tmp + 1;
