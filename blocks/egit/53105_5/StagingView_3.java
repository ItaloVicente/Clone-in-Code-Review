	private int getMaxLimitForListMode() {
		return Activator.getDefault().getPreferenceStore()
				.getInt(UIPreferences.STAGING_VIEW_MAX_LIMIT_LIST_MODE);
	}

	private static int getUnstaged(@Nullable IndexDiffData indexDiff) {
		if (indexDiff == null) {
			return 0;
		}
		int size = indexDiff.getUntracked().size();
		size += indexDiff.getMissing().size();
		size += indexDiff.getModified().size();
		size += indexDiff.getConflicting().size();
		return size;
	}

	private static int getStaged(@Nullable IndexDiffData indexDiff) {
		if (indexDiff == null) {
			return 0;
		}
		int size = indexDiff.getAdded().size();
		size += indexDiff.getChanged().size();
		size += indexDiff.getRemoved().size();
		return size;
	}

	private int updateAutoExpand(TreeViewer viewer, int newSize) {
		if (newSize > getMaxLimitForListMode()) {
			disableAutoExpand(viewer);
		}
		return newSize;
	}

	private void switchToCompactModeInternal(boolean auto) {
		setPresentation(Presentation.COMPACT_TREE, auto);
		listPresentationAction.setChecked(false);
		treePresentationAction.setChecked(false);
		if (auto) {
			setExpandCollapseActionsVisible(false);
		} else {
			setExpandCollapseActionsVisible(isExpandAllowed());
		}
	}

