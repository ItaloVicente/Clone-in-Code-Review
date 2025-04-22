	   /**
	    * This listener is added to the tooltip so that it can either dispose itself if the mouse
	    * exits the tooltip or so it can pass the selection event through to the table.
	    */
	    final TooltipLabelListener tooltipLabelListener = new TooltipLabelListener();
	    final class TooltipLabelListener implements Listener {
	        private boolean isCTRLDown(Event e) {
	        	return (e.stateMask & SWT.CTRL) != 0;
	        }
