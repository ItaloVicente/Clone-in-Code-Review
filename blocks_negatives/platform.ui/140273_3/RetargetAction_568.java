        if (handler != null) {
            handler.removePropertyChangeListener(propertyChangeListener);
            handler = null;
        }
        IWorkbenchPart part = getActivePart();
        if (part != null) {
            IWorkbenchPartSite site = part.getSite();
            SubActionBars bars = (SubActionBars) ((PartSite) site).getActionBars();
            bars.removePropertyChangeListener(propertyChangeListener);
        }
    }
