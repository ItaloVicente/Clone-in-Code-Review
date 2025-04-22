        super.setHelpListener(e -> {
		    HelpListener listener = null;
		    if (retargetAction != null) {
				listener = retargetAction.getHelpListener();
			}
		    if (listener == null) {
		        listener = localHelpListener;
			}
		    if (listener != null) {
		        listener.helpRequested(e);
			}
		});
