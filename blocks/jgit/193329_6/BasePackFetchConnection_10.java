				boolean mayHaveShallow = depth != null || deepenSince != null || !deepenNots.isEmpty();
				Set<ObjectId> shallowCommits = local.getObjectDatabase().getShallowCommits();
				if (isCapableOf(GitProtocolConstants.CAPABILITY_SHALLOW)) {
					sendShallow(shallowCommits
				} else if (mayHaveShallow) {
					throw new PackProtocolException(JGitText.get().shallowNotSupported);
				}
