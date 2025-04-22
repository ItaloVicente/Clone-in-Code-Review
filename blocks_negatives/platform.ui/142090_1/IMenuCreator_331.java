    /**
     * Returns an SWT menu created as a drop down menu parented by the
     * given menu.  In most cases, this menu can be created once, cached and reused
     * when the pop-up/drop-down action occurs.  If the menu must be dynamically
     * created (i.e., each time it is popped up or dropped down), the old menu
     * should be disposed of before replacing it with the new menu.
     *
     * @param parent the parent menu
     * @return the menu, or <code>null</code> if the menu could not
     *  be created
     */
    public Menu getMenu(Menu parent);
