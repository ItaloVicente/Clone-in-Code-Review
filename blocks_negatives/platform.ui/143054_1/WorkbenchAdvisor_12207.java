	 * If <code>flags</code> does include <code>FILL_PROXY</code>, then
	 * this is a request to describe the actions bars of the given workbench
	 * window (which will already have been filled); again, the remaining flags
	 * indicate which combination of the menu bar, the tool bar, and the status
	 * line are to be described. The actions included in the proxy action bars
	 * can be the same instances as in the actual window's action bars. Calling
	 * <code>ActionFactory</code> to create new action instances is not
	 * recommended, because these actions internally register listeners with the
	 * window and there is no opportunity to dispose of these actions.
