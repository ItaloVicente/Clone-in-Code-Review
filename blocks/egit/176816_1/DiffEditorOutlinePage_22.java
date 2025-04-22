				boolean compact = isChecked();
				preferences.setValue(UIPreferences.DIFF_OUTLINE_PRESENTATION,
						compact);
				((DiffContentProvider) getTreeViewer().getContentProvider())
						.setCompactTree(compact);
				getTreeViewer().setInput(getTreeViewer().getInput());
