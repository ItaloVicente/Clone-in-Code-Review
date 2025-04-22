		try (Git git = new Git(db)) {
			if (tagName != null) {
				if (delete) {
					git.tagDelete().setTags(tagName).call();
				} else {
					TagCommand command = git.tag().setForceUpdate(force)
							.setName(tagName);
					if (message != null) {
						command.setAnnotated(true).setMessage(message);
					} else {
						command.setAnnotated(false);
					}
