		class InverseHistoryAction extends Action {
			private boolean reversed;

			public InverseHistoryAction() {
				setText("Display in reverse order"); //$NON-NLS-1$

				reversed = RebaseInteractivePreferences.isOrderReversed();
				setChecked(reversed);
			}

			@Override
			public void run() {
				reversed = !reversed;

				RebaseInteractivePreferences.setOrderReversed(reversed);
				setChecked(reversed);

				TreeItem topmostVisibleItem = planTreeViewer.getTree()
						.getTopItem();
				refreshUI();
				if (topmostVisibleItem != null)
					planTreeViewer.getTree().showItem(topmostVisibleItem);
			}
		}

		IMenuManager menuMngr = getViewSite().getActionBars().getMenuManager();
		menuMngr.add(new InverseHistoryAction());

