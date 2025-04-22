					if (time.equals("upstream")) {
						if (ref == null) {
							String refName = new String(revChars
							ref = getRef(refName);
							if (ref == null)
								throw new RevisionSyntaxException(revstr);
						}
						String remoteBranchName = getConfig()
								.getString(
										ConfigConstants.CONFIG_BRANCH_SECTION
										ref.getName()
										ConfigConstants.CONFIG_KEY_MERGE);
						ref = getRef(remoteBranchName);
						if (ref == null)
							throw new RevisionSyntaxException(revstr);
					} else if (time.matches("^-\\d+$")) {
