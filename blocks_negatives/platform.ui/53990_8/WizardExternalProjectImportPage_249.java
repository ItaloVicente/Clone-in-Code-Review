    private Listener locationModifyListener = new Listener() {
        @Override
		public void handleEvent(Event e) {
            setPageComplete(validatePage());
        }
    };
