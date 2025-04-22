
				@Override
				public void run() {
					filter.setSelected(!filter.isSelected());
					postChangeAction.run();
				}
			}
		}

		private void createSelectShownRefsAction() {
			selectShownRefsAction = new SelectShownRefsAction();
			selectShownRefsAction.setImageDescriptor(UIIcons.BRANCH);
			selectShownRefsAction
					.setToolTipText(UIText.GitHistoryPage_selectShownRefs);
			actionsToDispose.add(selectShownRefsAction);
