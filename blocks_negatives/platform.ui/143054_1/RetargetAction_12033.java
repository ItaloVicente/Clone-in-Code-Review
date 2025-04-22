        super.partActivated(part);
        IWorkbenchPartSite site = part.getSite();
        SubActionBars bars = (SubActionBars) ((PartSite) site).getActionBars();
        bars.addPropertyChangeListener(propertyChangeListener);
        setActionHandler(bars.getGlobalActionHandler(getId()));
    }
