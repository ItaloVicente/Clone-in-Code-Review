		private class SelectShownRefsAction extends DropDownMenuAction
				implements IPropertyChangeListener {

			public SelectShownRefsAction() {
				super(UIText.GitHistoryPage_SelectShownRefsMenuLabel);
				historyPage.addPropertyChangeListener(this);
			}

			@Override
			public void dispose() {
				historyPage.removePropertyChangeListener(this);
				super.dispose();
			}

			@Override
			protected Collection<IAction> getActions() {
				if (historyPage.getCurrentRepo() == null)
					return new ArrayList<>();
				RefFilterHelper helper = historyPage.helper;
				List<IAction> actions = new ArrayList<>();
				actions.add(configureFiltersAction);
				Set<RefFilter> filters = helper.getRefFilters();
				List<RefFilter> sortedFilters = new ArrayList<>(
						filters);
				sortedFilters.sort(new Comparator<RefFilter>() {

					private int category(RefFilter filter) {
						if (filter.isPreconfigured())
							return 100;
						return 1000;
					}

					@Override
					public int compare(RefFilter o1, RefFilter o2) {
						int cat1 = category(o1);
						int cat2 = category(o2);

						if (cat1 != cat2) {
							return cat1 - cat2;
						}

						String name1 = o1.getFilterString();
						String name2 = o2.getFilterString();

						return name1.compareTo(name2);
					}
				});

				for (RefFilter filter : sortedFilters) {
					Action action = new ShownRefAction(filter, () -> {
						helper.setRefFilters(filters);
						historyPage.refresh();
					});
					actions.add(action);
				}
				return actions;
			}

			@Override
			public void run() {
				if (historyPage.getCurrentRepo() == null)
					return;
				RefFilterHelper helper = historyPage.helper;
				Set<RefFilter> filters = helper.getRefFilters();

				if (helper.isOnlyHEADSelected(filters)) {
					helper.restoreLastSelectionState(filters);
				} else {
					helper.saveSelectionStateAsLastSelectionState(filters);
					helper.selectOnlyHEAD(filters);
				}
				helper.setRefFilters(filters);
				historyPage.refresh(historyPage.selectedCommit());
			}

			private class ShownRefAction extends Action {

				private RefFilter filter;

				private Runnable postChangeAction;

				public ShownRefAction(RefFilter filter,
						Runnable postChangeAction) {
					super(filter.getFilterString(), IAction.AS_CHECK_BOX);
					if (filter.isPreconfigured()) {
						this.setText(filter.getFilterString()
								+ UIText.GitHistoryPage_filterRefDialog_preconfiguredText);
					}
					this.filter = filter;
					this.postChangeAction = postChangeAction;
				}
