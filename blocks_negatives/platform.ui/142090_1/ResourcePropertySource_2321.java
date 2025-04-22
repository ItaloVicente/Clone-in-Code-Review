    /**
     * Creates a PropertySource and stores its IResource
     *
     * @param res the resource for which this is a property source
     */
    public ResourcePropertySource(IResource res) {
        Assert.isNotNull(res);
        this.element = res;
    }
