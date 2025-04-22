					if (object != null) {
						try (RevWalk walk = new RevWalk(db)) {
							command.setObjectId(walk.parseAny(object));
						}
					}
					try {
						command.call();
					} catch (RefAlreadyExistsException e) {
						throw die(MessageFormat.format(
								CLIText.get().tagAlreadyExists
					}
				}
			} else {
				ListTagCommand command = git.tagList();
				List<Ref> list = command.call();
				for (Ref ref : list) {
					outw.println(Repository.shortenRefName(ref.getName()));
				}
