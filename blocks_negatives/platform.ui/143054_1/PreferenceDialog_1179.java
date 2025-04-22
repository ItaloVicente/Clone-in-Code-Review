		    	for(Control currentControl = pageContainer; currentControl != null; currentControl = currentControl.getParent()) {
		    		if (currentControl.isListening(SWT.Help)) {
		    			currentControl.notifyListeners(SWT.Help, new Event());
		    			break;
		    		}
		    	}
