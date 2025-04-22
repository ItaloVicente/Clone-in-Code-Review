    /**
     * Returns whether the given element is considered contained
     * in the specified containment context or if it is the context
     * itself.
     *
     * @param containmentContext object that provides containment
     * 	context for the element. This is typically a container object
     * 	(e.g., IFolder) and may be the element object itself.
     * @param element object that should be tested for containment
     * @param flags one or more of <code>CHECK_CONTEXT</code>,
     *    <code>CHECK_IF_CHILD</code>, <code>CHECK_IF_ANCESTOR</code>,
     *    <code>CHECK_IF_DESCENDENT</code> logically ORed together.
     */
    boolean contains(Object containmentContext, Object element, int flags);
