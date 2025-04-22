				if (reqBuilder.getDeepenSince() != 0) {
					throw new PackProtocolException(
							JGitText.get().deepenSinceWithDeepen);
				}
				if (reqBuilder.hasDeepenNots()) {
					throw new PackProtocolException(
							JGitText.get().deepenNotWithDeepen);
				}
