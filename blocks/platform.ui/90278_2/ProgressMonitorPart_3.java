        	fStopButton.addSelectionListener(widgetSelectedAdapter(e -> {
				setCanceled(true);
				if (fStopButton != null) {
					fStopButton.setEnabled(false);
				}
			}));
