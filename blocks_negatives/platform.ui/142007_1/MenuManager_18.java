    protected Item getMenuItem(int index) {
    	if (menu !=null) {
    		return menu.getItem(index);
    	}
    	return null;
    }

    /**
     * Get the menu item count for the implementation's widget.
     *
     * @return the number of items
     * @since 3.4
     */
    protected int getMenuItemCount() {
    	if (menu != null) {
    		return menu.getItemCount();
    	}
    	return 0;
    }

    /**
