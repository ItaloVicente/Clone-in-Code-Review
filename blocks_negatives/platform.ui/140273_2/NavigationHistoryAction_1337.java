    	super.dispose();
    	if (historyMenu != null) {
    		for (int i = 0; i < historyMenu.getItemCount(); i++) {
    			MenuItem menuItem = historyMenu.getItem(i);
    			menuItem.dispose();
    		}
    		historyMenu.dispose();
    		historyMenu = null;
    	}
    }
