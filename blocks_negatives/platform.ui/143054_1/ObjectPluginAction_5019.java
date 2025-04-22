    	if (activePart!=null) {
    		activePart.getSite().getPage().removePartListener(this);
    		activePart = null;
    	}
    	super.dispose();
    }
