		if (Depth.isSet(this.depth)) {
			command.setTagOpt(TagOpt.AUTO_FOLLOW);
		} else {
			command.setTagOpt(TagOpt.FETCH_TAGS);
		}
		command.setDepth(this.depth);
