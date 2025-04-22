        super.setHelpListener(new HelpListener() {
            @Override
			public void helpRequested(HelpEvent e) {
                HelpListener listener = null;
                if (handler != null) {
                    listener = handler.getHelpListener();
                    if (listener == null) {
                        listener = localHelpListener;
					}
                }
                if (listener != null) {
                    listener.helpRequested(e);
