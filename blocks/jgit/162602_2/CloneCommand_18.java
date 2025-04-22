		if (tagOption != null) {
			command.setTagOpt(tagOption);
		} else {
			command.setTagOpt(
					fetchAll ? TagOpt.FETCH_TAGS : TagOpt.AUTO_FOLLOW);
		}
