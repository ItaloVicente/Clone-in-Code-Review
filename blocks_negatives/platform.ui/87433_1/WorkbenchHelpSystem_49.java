		action.setHelpListener(new HelpListener() {
			@Override
			public void helpRequested(HelpEvent event) {
				Object[] helpContexts = computer.computeContexts(event);
				if (helpContexts != null && helpContexts.length > 0
						&& getHelpUI() != null) {
					IContext context = null;
					if (helpContexts[0] instanceof String) {
						context = HelpSystem
								.getContext((String) helpContexts[0]);
					} else if (helpContexts[0] instanceof IContext) {
						context = (IContext) helpContexts[0];
					}
					if (context != null) {
						Point point = computePopUpLocation(event.widget
								.getDisplay());
						displayContext(context, point.x, point.y);
					}
