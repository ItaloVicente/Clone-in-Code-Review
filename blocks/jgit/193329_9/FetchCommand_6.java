			if (depth != null) {
				transport.setDepth(depth);
			}
			if (unshallow) {
				if (depth != null) {
					throw new IllegalStateException(JGitText.get().depthWithUnshallow);
				}
				transport.setDepth(Constants.INFINITE_DEPTH);
			}
			transport.setDeepenSince(deepenSince);
			transport.setDeepenNots(shallowExcludes);
