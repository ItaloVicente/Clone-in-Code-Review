		action.setHelpListener(new HelpListener() {
			@Override
			public void helpRequested(HelpEvent event) {
				if (getHelpUI() != null) {
					IContext context = HelpSystem.getContext(contextId);
					if (context != null) {
						Point point = computePopUpLocation(event.widget
								.getDisplay());
						String title = LegacyActionTools.removeMnemonics(action.getText());
						displayContext(new ContextWithTitle(context, title), point.x, point.y);
					}
