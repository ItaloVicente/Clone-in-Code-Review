    /**
     * This method calls:
     * <ul>
     *  <li><code>contributeToMenu</code> with <code>bars</code>' menu manager</li>
     *  <li><code>contributeToToolBar</code> with <code>bars</code>' tool bar
     *    manager</li>
     *  <li><code>contributeToCoolBar</code> with <code>bars</code>' cool bar
     *    manager if <code>IActionBars</code> is of extended type <code>IActionBars2</code> </li>
     *  <li><code>contributeToStatusLine</code> with <code>bars</code>' status line
     *    manager</li>
     * </ul>
     * The given action bars are also remembered and made accessible via
     * <code>getActionBars</code>.
     *
     * @param bars the action bars
     */
    public void init(IActionBars bars) {
        this.bars = bars;
        contributeToMenu(bars.getMenuManager());
        contributeToToolBar(bars.getToolBarManager());
        if (bars instanceof IActionBars2) {
            contributeToCoolBar(((IActionBars2) bars).getCoolBarManager());
        }
        contributeToStatusLine(bars.getStatusLineManager());
