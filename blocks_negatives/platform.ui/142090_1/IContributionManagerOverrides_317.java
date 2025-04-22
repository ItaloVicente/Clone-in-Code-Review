    /**
     * Find out the enablement of the item
     * @param item the contribution item for which the enable override value is
     * determined
     * @return <ul>
     * 				<li><code>Boolean.TRUE</code> if the given contribution item should be enabled</li>
     * 				<li><code>Boolean.FALSE</code> if the item should be disabled</li>
     * 				<li><code>null</code> if the item may determine its own enablement</li>
     * 			</ul>
     * @since 2.0
     */
    public Boolean getEnabled(IContributionItem item);
