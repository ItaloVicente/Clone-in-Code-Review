				if (l - i > 1) {
					String distnum = new String(rev
					try {
						dist = Integer.parseInt(distnum);
					} catch (NumberFormatException e) {
						throw new RevisionSyntaxException(
								JGitText.get().invalidAncestryLength
					}
				} else
					dist = 1;
