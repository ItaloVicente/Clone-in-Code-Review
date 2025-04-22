	 * @param force
	 *            <code>true</code> means update even if not dirty, and
	 *            <code>false</code> for normal incremental updating
	 * @param recursive
	 *            <code>true</code> means recursively update all submenus, and
	 *            <code>false</code> means just this menu
	 */
    protected void update(boolean force, boolean recursive) {
        if (isDirty() || force) {
            if (menuExist()) {
                IContributionItem[] items = getItems();
                List<IContributionItem> clean = new ArrayList<>(items.length);
                IContributionItem separator = null;
                for (IContributionItem item : items) {
                    IContributionItem ci = item;
                    if (!isChildVisible(ci)) {
