    	tree.addListener(SWT.SetData, new Listener(){

			@Override
			public void handleEvent(Event event) {
				setDataCalls++;
			}});
