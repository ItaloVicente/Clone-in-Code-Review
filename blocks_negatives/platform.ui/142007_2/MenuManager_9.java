        MenuManagerEventHelper.getInstance().showEventPreHelper(this);
        fireAboutToShow(this);
        MenuManagerEventHelper.getInstance().showEventPostHelper(this);
        update(false, false);
    }

    /**
     * Notifies all listeners that this menu is about to disappear.
     */
    private void handleAboutToHide() {
    	MenuManagerEventHelper.getInstance().hideEventPreHelper(this);
        fireAboutToHide(this);
        MenuManagerEventHelper.getInstance().hideEventPostHelper(this);
    }

    /**
     * Initializes the menu control.
     */
    private void initializeMenu() {
        menu.addMenuListener(new MenuAdapter() {
            @Override
