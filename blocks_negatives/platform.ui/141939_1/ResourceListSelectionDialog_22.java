        return text;
    }

    /**
     * Returns whether derived resources should be shown in the list.
     * The default is <code>false</code>.
     *
     * @return <code>true</code> to show derived resources, <code>false</code> to hide them
     * @since 3.1
     */
    protected boolean getShowDerived() {
       return showDerived ;
    }

    /**
     * Sets whether derived resources should be shown in the list.
     *
     * @param show <code>true</code> to show derived resources, <code>false</code> to hide them
     * @since 3.1
     */
    protected void setShowDerived(boolean show) {
        showDerived  = show;
    }

    /**
     * Creates a ResourceDescriptor for each IResource,
     * sorts them and removes the duplicated ones.
     *
     * @param resources resources to create resource descriptors for
     */
    private void initDescriptors(final IResource resources[]) {
        BusyIndicator.showWhile(null, () -> {
		    descriptors = new ResourceDescriptor[resources.length];
		    for (int i1 = 0; i1 < resources.length; i1++) {
		        IResource r = resources[i1];
		        ResourceDescriptor d = new ResourceDescriptor();
		        d.label = r.getName();
		        d.resources.add(r);
		        descriptors[i1] = d;
		    }
		    Arrays.sort(descriptors);
		    descriptorsSize = descriptors.length;

		    int index = 0;
		    if (descriptorsSize < 2) {
