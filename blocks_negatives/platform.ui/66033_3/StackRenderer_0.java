	private EventHandler itemUpdater;

	private EventHandler dirtyUpdater;

	/**
	 * An event handler for listening to changes to the state of view menus and
	 * its child menu items. Depending on what state these items are in, the
	 * view menu should or should not be rendered in the tab folder.
	 */
	private EventHandler viewMenuUpdater;

	/**
	 * An event handler for listening to changes to the children of an element
	 * container. The tab folder may need to layout itself again if a part's
	 * toolbar has been changed.
	 */
	private EventHandler tabStateHandler;

	private EventHandler stylingHandler;

