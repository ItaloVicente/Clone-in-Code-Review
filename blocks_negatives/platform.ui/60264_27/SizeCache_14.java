    public int computeMinimumWidth() {
        if (minimumWidth == -1) {
    		if (control instanceof Composite) {
    			Layout layout = ((Composite)control).getLayout();
    			if (layout instanceof ILayoutExtension) {
    				minimumWidth = ((ILayoutExtension)layout).computeMinimumWidth((Composite)control, flushChildren);
    				flushChildren = false;
    			}
    		}
        }
