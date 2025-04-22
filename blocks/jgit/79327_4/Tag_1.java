				if (delete) {
					List<String> deletedTags = git.tagDelete().setTags(tagName)
							.call();
					if (deletedTags.isEmpty()) {
						throw die(MessageFormat
								.format(CLIText.get().tagNotFound
					}
				} else {
					TagCommand command = git.tag().setForceUpdate(force)
							.setMessage(message).setName(tagName);
