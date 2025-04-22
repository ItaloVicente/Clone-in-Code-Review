
				@Override
				public void run() {
					if (historyPage.getCurrentRepo() == null)
						return;
					filter.setSelected(!filter.isSelected());
					postChangeAction.run();
				}
			}

			@Override
			public void propertyChange(PropertyChangeEvent event) {
				if (P_REPOSITORY.equals(event.getProperty())) {
					Repository currentRepo = historyPage.getCurrentRepo();
					if (currentRepo == null) {
						this.setEnabled(false);
						helper = null;
					} else {
						this.setEnabled(true);
						helper = new RefFilterHelper(currentRepo);
						setHeadModeFromHelperState();
						updateUiForMode();
					}
				}
			}
		}

		private void createSelectShownRefsAction() {
			selectShownRefsAction = new SelectShownRefsAction();
			actionsToDispose.add(selectShownRefsAction);
