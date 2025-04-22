	     * The listener that gets added to the table.  This listener is responsible for creating the tooltips
	     * when hovering over a cell item. This listener will listen for the following events:
	     *  <li>SWT.KeyDown		- to remove the tooltip</li>
	     *  <li>SWT.Dispose		- to remove the tooltip</li>
	     *  <li>SWT.MouseMove	- to remove the tooltip</li>
	     *  <li>SWT.MouseHover	- to set the tooltip</li>
	     */
	    Listener tableListener = new Listener () {
	    	Shell tooltip = null;
	    	Label label = null;

	    	@Override
