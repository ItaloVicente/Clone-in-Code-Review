		String secondRefName = Constants.HEAD;
		if (getSelectedNodes(event).size() == 2) {
			secondRefName = getRefName(getSelectedNodes(event).get(1));
			if (secondRefName == null)
				return null;
		}

		final String secondRefNameParam = secondRefName;

		final boolean includeLocal = getSelectedNodes(event).size() == 1;

