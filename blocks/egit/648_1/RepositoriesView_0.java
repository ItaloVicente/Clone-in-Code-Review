
		IAction collapseAllAction = new Action(
				UIText.RepositoriesView_CollapseAllMenu) {

			@Override
			public void run() {
				tv.collapseAll();
			}

		};

		collapseAllAction.setImageDescriptor(UIIcons.COLLAPSEALL);

		getViewSite().getActionBars().getToolBarManager()
				.add(collapseAllAction);
