     * Adapt the given element to an <code>IResource</code> using the following
     * search order:
     * <ol>
     * <li> using the IContributorResourceAdapter registered for the given element, or
     * <li> directly asking the element if it adapts.
     * </ol>
     *
     * @param element the element to adapt
     * @return an <code>IResource</code> instance if the element could be adapted or <code>null</code>
     * otherwise.
     * @since 3.1
     */
    public static Object getAdaptedResource(Object element) {
