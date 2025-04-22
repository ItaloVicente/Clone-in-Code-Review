		action.setHelpListener(new HelpListener() {
			@Override
			public void helpRequested(HelpEvent event) {
				if (contexts != null && contexts.length > 0
						&& getHelpUI() != null) {
					IContext context = null;
					if (contexts[0] instanceof String) {
						context = HelpSystem.getContext((String) contexts[0]);
					} else if (contexts[0] instanceof IContext) {
						context = (IContext) contexts[0];
					}
					if (context != null) {
						Point point = computePopUpLocation(event.widget
								.getDisplay());
						displayContext(context, point.x, point.y);
					}
