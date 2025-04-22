    public int computeMaximumWidth() {
        if (maximumWidth == -1) {
    		if (control instanceof Composite) {
    			Layout layout = ((Composite)control).getLayout();
    			if (layout instanceof ILayoutExtension) {
    				maximumWidth = ((ILayoutExtension)layout).computeMaximumWidth((Composite)control, flushChildren);
    				flushChildren = false;
    			}
    		}
        }

        if (maximumWidth == -1) {
            maximumWidth = getPreferredSize().x;
        }
