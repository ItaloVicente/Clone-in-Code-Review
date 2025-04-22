		if (object == null) {
			object = db.resolve(Constants.HEAD);
			if (object == null)
				throw die(MessageFormat.format(CLIText.get().cannotResolve, Constants.HEAD));
		}

		if (!tagName.startsWith(Constants.R_TAGS))
			tagName = Constants.R_TAGS + tagName;
