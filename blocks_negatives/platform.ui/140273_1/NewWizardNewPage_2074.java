    /**
     * @param wizard
     * @return whether the given wizard is primary
     */
    private boolean isPrimary(IWizardDescriptor wizard) {
        for (IWizardDescriptor primaryWizard : primaryWizards) {
            if (primaryWizard.equals(wizard)) {
