		return new HelpListener() {
			@Override
			public void helpRequested(HelpEvent event) {
				if (getHelpUI() != null) {
					IContext context = HelpSystem.getContext(contextId);
					if (context != null) {
						Point point = computePopUpLocation(event.widget
								.getDisplay());
						displayContext(context, point.x, point.y);
					}
