		}		
	}

	/**
     * Updates the given element's presentation when one or more of its
     * properties changes. Only the given element is updated.
     * <p>
     * EXPERIMENTAL.  Not to be used except by JDT.
     * This method was added to support JDT's explorations
     * into grouping by working sets, which requires viewers to support multiple 
     * equal elements.  See bug 76482 for more details.  This support will
     * likely be removed in Eclipse 3.3 in favor of proper support for
     * multiple equal elements (which was implemented for AbtractTreeViewer in 3.2). 
     * </p>
     * @param widget
     *            the widget for the element
     * @param element
     *            the element
     * @param properties
     *            the properties that have changed, or <code>null</code> to
     *            indicate unknown
     */
	protected void internalUpdate(Widget widget, Object element, String[] properties) {
