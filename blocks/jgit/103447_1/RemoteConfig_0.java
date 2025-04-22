		try {
			tagopt = rc.getEnum(TagOpt.values()
					TagOpt.AUTO_FOLLOW);
		} catch (IllegalArgumentException e) {
			tagopt = TagOpt.AUTO_FOLLOW;
		}
