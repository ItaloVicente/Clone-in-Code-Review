        if (element instanceof IWorkbenchPart) {
            IWorkbenchPart part = (IWorkbenchPart) element;
            String path = part.getTitleToolTip();
            if (path == null || path.trim().length() == 0) {
                return part.getTitle();
            }
        }
        if (element instanceof Saveable) {
        	Saveable model = (Saveable) element;
            String path = model.getToolTipText();
            if (path == null || path.trim().length() == 0) {
                return model.getName();
            }
