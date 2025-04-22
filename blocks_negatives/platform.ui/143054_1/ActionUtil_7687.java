    /**
     * Fire the "handleAboutToShow" method in a menu manager.
     * This triggers the same behavior as when a user opens a menu.
     * The menu to be populated with actions and those
     * actions to be enacted in SWT widgets.
     *
     * @param mgr the menu manager to open
     */
    public static void fireAboutToShow(MenuManager mgr) throws Throwable {
		Class<?> clazz = mgr.getClass();
        Method method = clazz.getDeclaredMethod("handleAboutToShow",
                new Class[0]);
        method.setAccessible(true);
        method.invoke(mgr, new Object[0]);
    }
