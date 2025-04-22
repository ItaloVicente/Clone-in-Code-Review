				boolean mayHaveShallow = depth != null || deepenSince != null || !deepenNots.isEmpty();
				if (isCapableOf(GitProtocolConstants.CAPABILITY_SHALLOW)) {
					sendShallow(output);
				} else if (mayHaveShallow) {
					throw new PackProtocolException(JGitText.get().shallowNotSupported);
				}
