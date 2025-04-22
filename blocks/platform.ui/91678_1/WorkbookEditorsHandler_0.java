	@Override
	protected int getCurrentItemIndex() {
		if (isMruEnabled()) {
			return 0;
		}

		WorkbenchPage page = (WorkbenchPage) window.getActivePage();
		List<EditorReference> sortedByUse = page.getSortedEditorReferences();
		if (sortedByUse.size() < 2) {
			return 0;
		}

		EditorReference next = sortedByUse.get(1);

		List<EditorReference> sortedByPosition = getParts(page);
		for (int i = 0; i < sortedByPosition.size(); i++) {
			EditorReference ref = sortedByPosition.get(i);
			if (ref == next) {
				if (i > 0) {
					gotoDirection = true;
					return i - 1;
				}
				gotoDirection = false;
				return 1;
			}
		}
		return super.getCurrentItemIndex();
	}

