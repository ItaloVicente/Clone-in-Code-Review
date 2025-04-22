        super.setHelpListener(e -> {
		    HelpListener listener = null;
		    if (handler != null) {
		        listener = handler.getHelpListener();
		        if (listener == null) {
		            listener = localHelpListener;
