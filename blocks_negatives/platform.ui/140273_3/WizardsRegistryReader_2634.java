     * Finishes the recognition of primary wizards.
     */
    private void finishPrimary() {
        if (deferPrimary != null) {
            ArrayList primary = new ArrayList();
            for (Iterator i = deferPrimary.iterator(); i.hasNext();) {
                String id = (String) i.next();
                WorkbenchWizardElement element = wizardElements == null ? null : wizardElements.findWizard(id, true);
                if (element != null) {
                    primary.add(element);
                }
            }

            primaryWizards = (WorkbenchWizardElement[]) primary
                    .toArray(new WorkbenchWizardElement[primary.size()]);

            deferPrimary = null;
        }
    }


    /**
     *	Insert the passed wizard element into the wizard collection appropriately
     *	based upon its defining extension's CATEGORY tag value
     *
     *	@param element WorkbenchWizardElement
     *	@param config configuration element
     */
    private void finishWizard(WorkbenchWizardElement element,
            IConfigurationElement config) {
        StringTokenizer familyTokenizer = new StringTokenizer(
                getCategoryStringFor(config), CATEGORY_SEPARATOR);

        boolean moveToOther = false;

        while (familyTokenizer.hasMoreElements()) {
            WizardCollectionElement tempCollectionElement = getChildWithID(
                    currentCollectionElement, familyTokenizer.nextToken());

                moveToOther = true;
                break;
            }
            currentCollectionElement = tempCollectionElement;
        }

        if (moveToOther) {
