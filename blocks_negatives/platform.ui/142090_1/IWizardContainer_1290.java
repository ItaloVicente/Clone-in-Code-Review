    /**
     * Makes the given page visible.
     * <p>
     * This method should not be use for normal page
     * sequencing (back, next) which is handled by the
     * container itself. It may, however, be used to
     * move to another page in response to some custom
     * action such as double clicking in a list.
     * </p>
     *
     * @param page the page to show
     * @see #getCurrentPage
     */
    public void showPage(IWizardPage page);
