    /**
     * Contribute the external menus and actions applicable for this view part.
     */
    private void contributeToPart(IViewPart part) {
        IActionBars bars = part.getViewSite().getActionBars();
        contribute(bars.getMenuManager(), bars.getToolBarManager(), true);
    }
