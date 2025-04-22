        return parent.getPath().append(getId());
    }


    @Override
	public IWizardDescriptor [] getWizards() {
		return getWizardsExpression((IWizardDescriptor[]) wizards
				.getTypedChildren(IWizardDescriptor.class));
	}

    /**
     * Takes an array of <code>IWizardDescriptor</code> and removes all
     * entries which fail the Expressions check.
     *
     * @param wizardDescriptors Array of <code>IWizardDescriptor</code>.
     * @return The array minus the elements which faled the Expressions check.
     */
    private IWizardDescriptor[] getWizardsExpression(IWizardDescriptor[] wizardDescriptors) {
        int size = wizardDescriptors.length;
        List result = new ArrayList(size);
        for (int i = 0; i < size; i++) {
            if (!WorkbenchActivityHelper.restrictUseOf(wizardDescriptors[i]))
                result.add(wizardDescriptors[i]);
        }
        return (IWizardDescriptor[])result
                    .toArray(new IWizardDescriptor[result.size()]);
    }

    /**
     * Return the wizards minus the wizards which failed the expressions check.
     *
     * @return the wizards
     * @since 3.1
     */
    public WorkbenchWizardElement [] getWorkbenchWizardElements() {
    	return getWorkbenchWizardElementsExpression(
    	    (WorkbenchWizardElement[]) wizards
				.getTypedChildren(WorkbenchWizardElement.class));
    }

    /**
     * Takes an array of <code>WorkbenchWizardElement</code> and removes all
     * entries which fail the Expressions check.
     *
     * @param workbenchWizardElements Array of <code>WorkbenchWizardElement</code>.
     * @return The array minus the elements which faled the Expressions check.
     */
    private WorkbenchWizardElement[] getWorkbenchWizardElementsExpression(
        WorkbenchWizardElement[] workbenchWizardElements) {
        int size = workbenchWizardElements.length;
        List result = new ArrayList(size);
        for (int i=0; i<size; i++) {
            WorkbenchWizardElement element = workbenchWizardElements[i];
            if (!WorkbenchActivityHelper.restrictUseOf(element))
                result.add(element);
        }
        return (WorkbenchWizardElement[])result.toArray(new WorkbenchWizardElement[result.size()]);
    }


    /**
     * Returns true if this element has no children and no wizards.
     *
     * @return whether it is empty
     */
    public boolean isEmpty() {
        return size() == 0 && wizards.size() == 0;
    }

    /**
     * For debugging purposes.
     */
    @Override
