    private Listener nameModifyListener = new Listener() {
        @Override
		public void handleEvent(Event e) {
        	setLocationForSelection();
            boolean valid = validatePage();
            setPageComplete(valid);
