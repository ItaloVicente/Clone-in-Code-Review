		return parent.getPath().append(getId());
	}

	@Override
	public IWizardDescriptor[] getWizards() {
		return getWizardsExpression((IWizardDescriptor[]) wizards.getTypedChildren(IWizardDescriptor.class));
	}

	private IWizardDescriptor[] getWizardsExpression(IWizardDescriptor[] wizardDescriptors) {
		int size = wizardDescriptors.length;
		List result = new ArrayList(size);
		for (int i = 0; i < size; i++) {
			if (!WorkbenchActivityHelper.restrictUseOf(wizardDescriptors[i]))
				result.add(wizardDescriptors[i]);
		}
		return (IWizardDescriptor[]) result.toArray(new IWizardDescriptor[result.size()]);
	}

	public WorkbenchWizardElement[] getWorkbenchWizardElements() {
		return getWorkbenchWizardElementsExpression(
				(WorkbenchWizardElement[]) wizards.getTypedChildren(WorkbenchWizardElement.class));
	}

	private WorkbenchWizardElement[] getWorkbenchWizardElementsExpression(
			WorkbenchWizardElement[] workbenchWizardElements) {
		int size = workbenchWizardElements.length;
		List result = new ArrayList(size);
		for (int i = 0; i < size; i++) {
			WorkbenchWizardElement element = workbenchWizardElements[i];
			if (!WorkbenchActivityHelper.restrictUseOf(element))
				result.add(element);
		}
		return (WorkbenchWizardElement[]) result.toArray(new WorkbenchWizardElement[result.size()]);
	}

	public boolean isEmpty() {
		return size() == 0 && wizards.size() == 0;
	}

	@Override
