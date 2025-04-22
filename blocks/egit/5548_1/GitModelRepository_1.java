	private void disposeOldChildren() {
		if (children == null)
			return;
		for (GitModelObject child : children)
			child.dispose();

	}

