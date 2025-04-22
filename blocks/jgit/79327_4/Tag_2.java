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
