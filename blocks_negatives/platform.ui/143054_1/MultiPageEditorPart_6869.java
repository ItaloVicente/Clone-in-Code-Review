		IEditorActionBarContributor contributor = getEditorSite()
				.getActionBarContributor();
		if (contributor != null
				&& contributor instanceof MultiPageEditorActionBarContributor) {
			((MultiPageEditorActionBarContributor) contributor)
					.setActivePage(activeEditor);
