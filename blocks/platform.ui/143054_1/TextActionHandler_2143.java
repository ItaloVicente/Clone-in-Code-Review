			if (event.getProperty().equals(IAction.ENABLED)) {
				Boolean bool = (Boolean) event.getNewValue();
				actionHandler.setEnabled(bool.booleanValue());
			}
		}
	}

	private class DeleteActionHandler extends Action {
		protected DeleteActionHandler() {
			super(CommonNavigatorMessages.Delete);
			setId("TextDeleteActionHandler");//$NON-NLS-1$
			setEnabled(false);
			PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
					INavigatorHelpContextIds.TEXT_DELETE_ACTION);
		}

		@Override
