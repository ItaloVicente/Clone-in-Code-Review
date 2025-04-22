	String getParentCommitLabel(int i) {
		switch (i) {
		case 0:
			return UIText.StashEditorPage_LabelParent0;
		case 1:
			return UIText.StashEditorPage_LabelParent1;
		case 2:
			return UIText.StashEditorPage_LabelParent2;
		default:
			throw new IllegalStateException("Unexpected parent with index" + i); //$NON-NLS-1$
		}
	}

