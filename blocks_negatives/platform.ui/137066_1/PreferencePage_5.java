     * Contributes additional buttons to the given composite.
     * <p>
     * The default implementation of this framework hook method does
     * nothing. Subclasses should override this method to contribute buttons
     * to this page's button bar. For each button a subclass contributes,
     * it must also increase the parent's grid layout number of columns
     * by one; that is,
     * <pre>
     * ((GridLayout) parent.getLayout()).numColumns++);
     * </pre>
     * </p>
     *
     * @param parent the button bar
     */
