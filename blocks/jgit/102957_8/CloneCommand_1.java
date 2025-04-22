		if ((this.depth > 0) && (this.depth < Transport.DEPTH_INFINITE)) {
			command.setTagOpt(TagOpt.AUTO_FOLLOW);
		} else {
			command.setTagOpt(TagOpt.FETCH_TAGS);
		}
		command.setDepth(this.depth);
