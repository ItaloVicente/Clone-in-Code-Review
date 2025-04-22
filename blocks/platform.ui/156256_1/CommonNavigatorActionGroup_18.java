		frameList.addPropertyChangeListener(new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				if (event.getProperty().equals(FrameList.P_RESET)) {
					upAction.setEnabled(false);
					backAction.setEnabled(false);
					forwardAction.setEnabled(false);

					upAction.update();
				}
				commonNavigator.updateTitle();
				IActionBars actionBars = commonNavigator.getViewSite().getActionBars();
				updateToolBar(actionBars.getToolBarManager());
				actionBars.updateActionBars();
