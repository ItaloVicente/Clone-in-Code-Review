	            toolBar.addListener(SWT.MenuDetect, new Listener() {

	                @Override
					public void handleEvent(Event event) {
	                    if (toolBarManager.getContextMenuManager() == null) {
	                        handleContextMenu(event);
	                    }
	                }
	            });
