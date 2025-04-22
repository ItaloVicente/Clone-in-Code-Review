			group(entries);
			limitEntriesCount();
			display.asyncExec(() -> {
				setContentDescription(getTitleSummary());
				fFilteredTree.getViewer().refresh();
				fFilteredTree.setEnabled(true);
			});
		});
