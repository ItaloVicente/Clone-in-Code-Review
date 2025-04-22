	static int getUnstaged(@Nullable IndexDiffData indexDiff) {
		if (indexDiff == null) {
			return 0;
		}
		int size = indexDiff.getUntracked().size();
		size += indexDiff.getMissing().size();
		size += indexDiff.getModified().size();
		size += indexDiff.getConflicting().size();
		return size;
	}

	static int getStaged(@Nullable IndexDiffData indexDiff) {
		if (indexDiff == null) {
			return 0;
		}
		int size = indexDiff.getAdded().size();
		size += indexDiff.getChanged().size();
		size += indexDiff.getRemoved().size();
		return size;
	}

	private int updateAutoExpand(TreeViewer viewer, int newSize) {
		if (newSize > 10000) {
			disableAutoExpand(viewer);
		}
		return newSize;
	}

	void switchToCompactModeInternal(boolean auto) {
		setPresentation(presentation, auto);
		listPresentationAction.setChecked(false);
		treePresentationAction.setChecked(false);
		if (auto) {
			setExpandCollapseActionsVisible(false);
		} else {
			setExpandCollapseActionsVisible(true);
		}
	}

