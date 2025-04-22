			switch (event.type) {
			case SWT.Activate:
				activeTextControl = (Text) event.widget;
				updateActionsEnableState();
				break;
			case SWT.Deactivate:
				activeTextControl = null;
				updateActionsEnableState();
				break;
			default:
				break;
			}
		}
	}

	private class PropertyChangeListener implements IPropertyChangeListener {
		private IAction actionHandler;

		protected PropertyChangeListener(IAction actionHandler) {
			super();
			this.actionHandler = actionHandler;
		}

		@Override
