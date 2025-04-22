		sortAction = new Action(UIText.StagingView_UnstagedSort,
				IAction.AS_CHECK_BOX) {

			@Override
			public void run() {
				UnstagedComparator comparator = (UnstagedComparator) unstagedViewer
						.getComparator();
				comparator.setAlphabeticallySortActive(isChecked());
				unstagedViewer.refresh();
			}
		};

		sortAction.setImageDescriptor(UIIcons.ALPHABETICALLY_SORT);
		sortAction.setId(SORT_ITEM_TOOLBAR_ID);
		sortAction.setChecked(getSortCheckState());

