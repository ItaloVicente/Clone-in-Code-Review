
			if (containsHelp(args)) {
				seenHelp = true;
				break;
			}
		}

		try {
			super.parseArgument(tmp.toArray(new String[tmp.size()]));
		} finally {
			seenHelp = false;
