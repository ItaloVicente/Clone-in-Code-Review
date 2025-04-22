		try {
			val = rc.getString(SECTION
			tagopt = TagOpt.fromOption(val);
		} catch (IllegalArgumentException e) {
			tagopt = TagOpt.AUTO_FOLLOW;
		}
