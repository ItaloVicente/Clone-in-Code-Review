    /**
     * Visiblity override.
     *
     * @param item the contribution item in question
     * @return  <ul>
     * 				<li><code>Boolean.TRUE</code> if the given contribution item should be visible</li>
     * 				<li><code>Boolean.FALSE</code> if the item should not be visible</li>
     * 				<li><code>null</code> if the item may determine its own visibility</li>
     * 			</ul>
     * @since 3.5
     */
    public Boolean getVisible(IContributionItem item);
