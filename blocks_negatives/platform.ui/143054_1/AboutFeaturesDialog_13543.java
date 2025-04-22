	   /**
     * Constructor for AboutFeaturesDialog.
     *
     * @param parentShell the parent shell
     * @param productName the product name
     * @param bundleGroupInfos the bundle info
     */
    public AboutFeaturesDialog(Shell parentShell, String productName,
            AboutBundleGroupData[] bundleGroupInfos, AboutBundleGroupData initialSelection) {
        super(parentShell);
        AboutFeaturesPage page = new AboutFeaturesPage();
        page.setProductName(productName);
        page.setBundleGroupInfos(bundleGroupInfos);
        page.setInitialSelection(initialSelection);
        String title;
        if (productName != null)
