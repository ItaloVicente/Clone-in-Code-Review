						src = branch;
						final Ref old = db.findRef(src);
						if (old == null) {
							throw die(MessageFormat
									.format(CLIText.get().doesNotExist
						}
						if (!old.getName().startsWith(Constants.R_HEADS)) {
							throw die(MessageFormat
									.format(CLIText.get().notABranch
						}
						src = old.getName();
						dst = otherBranch;
