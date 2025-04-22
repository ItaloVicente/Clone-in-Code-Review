
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
					if (historyPage.getCurrentRepo() == null) {
						this.setEnabled(false);
					} else {
						this.setEnabled(true);
					}
				}
			}
		}

		private void createSelectShownRefsAction() {
			selectShownRefsAction = new SelectShownRefsAction();
			selectShownRefsAction.setImageDescriptor(UIIcons.BRANCH);
			selectShownRefsAction
					.setToolTipText(UIText.GitHistoryPage_selectShownRefs);
			actionsToDispose.add(selectShownRefsAction);
