					ObjectId id = notFound.getObjectId();
					if (wantIds.contains(id)) {
						String msg = MessageFormat.format(
								JGitText.get().wantNotValid, id.name());
						throw new PackProtocolException(msg, notFound);
					}
