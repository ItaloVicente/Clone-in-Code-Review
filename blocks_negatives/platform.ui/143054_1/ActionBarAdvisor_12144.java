    /**
     * Configures the action bars using the given action bar configurer.
     * Under normal circumstances, <code>flags</code> does not include
     * <code>FILL_PROXY</code>, meaning this is a request to fill the action
     * bars of the corresponding workbench window; the
     * remaining flags indicate which combination of
     * the menu bar (<code>FILL_MENU_BAR</code>),
     * the tool bar (<code>FILL_COOL_BAR</code>),
     * and the status line (<code>FILL_STATUS_LINE</code>) are to be filled.
     * <p>
     * If <code>flags</code> does include <code>FILL_PROXY</code>, then this
     * is a request to describe the actions bars of the given workbench window
     * (which will already have been filled);
     * again, the remaining flags indicate which combination of the menu bar,
     * the tool bar, and the status line are to be described.
     * The actions included in the proxy action bars can be the same instances
     * as in the actual window's action bars. Calling <code>ActionFactory</code>
     * to create new action instances is not recommended, because these
     * actions internally register listeners with the window and there is no
     * opportunity to dispose of these actions.
     * </p>
     * <p>
     * This method is called just after {@link WorkbenchWindowAdvisor#preWindowOpen()}.
     * Clients must not call this method directly (although super calls are okay).
     * The default implementation calls <code>makeActions</code> if
     * <code>FILL_PROXY</code> is specified, then calls <code>fillMenuBar</code>,
     * <code>fillCoolBar</code>, and <code>fillStatusLine</code>
     * if the corresponding flags are specified.
     * </p>
     * <p>
     * Subclasses may override, but it is recommended that they override the
     * methods mentioned above instead.
     * </p>
     *
     * @param flags bit mask composed from the constants
     * {@link #FILL_MENU_BAR FILL_MENU_BAR},
     * {@link #FILL_COOL_BAR FILL_COOL_BAR},
     * {@link #FILL_STATUS_LINE FILL_STATUS_LINE},
     * and {@link #FILL_PROXY FILL_PROXY}
     */
    public void fillActionBars(int flags) {
        if ((flags & FILL_PROXY) == 0) {
            makeActions(actionBarConfigurer.getWindowConfigurer().getWindow());
        }
        if ((flags & FILL_MENU_BAR) != 0) {
            fillMenuBar(actionBarConfigurer.getMenuManager());
        }
        if ((flags & FILL_COOL_BAR) != 0) {
            fillCoolBar(actionBarConfigurer.getCoolBarManager());
        }
        if ((flags & FILL_STATUS_LINE) != 0) {
            fillStatusLine(actionBarConfigurer.getStatusLineManager());
        }
    }
