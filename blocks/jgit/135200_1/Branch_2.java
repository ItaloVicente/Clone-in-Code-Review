					if (!dst.startsWith(Constants.R_HEADS)) {
						dst = Constants.R_HEADS + dst;
					}
					if (!Repository.isValidRefName(dst)) {
						throw die(MessageFormat
								.format(CLIText.get().notAValidRefName
					}
