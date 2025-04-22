	private static String getDescription(Object object) {
		if (object instanceof DisplayItem) {
			DisplayItem item = (DisplayItem) object;

			if (isNewWizard(item)) {
				ShortcutItem shortcut = (ShortcutItem) item;
				IWizardDescriptor descriptor = (IWizardDescriptor) shortcut
						.getDescriptor();
				return descriptor.getDescription();
			}

			if (isShowPerspective(item)) {
				ShortcutItem shortcut = (ShortcutItem) item;
				IPerspectiveDescriptor descriptor = (IPerspectiveDescriptor) shortcut
						.getDescriptor();
				return descriptor.getDescription();
			}

			if (isShowView(item)) {
				ShortcutItem shortcut = (ShortcutItem) item;
				IViewDescriptor descriptor = (IViewDescriptor) shortcut
						.getDescriptor();
				return descriptor.getDescription();
			}

			if (item instanceof DynamicContributionItem) {
				return WorkbenchMessages.HideItems_dynamicItemDescription;
			}

			IContributionItem contrib = item.getIContributionItem();
			return getDescription(contrib);
		}

		if (object instanceof ActionSet) {
			ActionSet actionSet = (ActionSet) object;
			return actionSet.descriptor.getDescription();
		}

		return null;
	}

	private static String getDescription(IContributionItem item) {
		if (item instanceof ActionContributionItem) {
			ActionContributionItem aci = (ActionContributionItem) item;
			IAction action = aci.getAction();
			if (action == null) {
				return null;
			}
			return action.getDescription();
		}
		if (item instanceof ActionSetContributionItem) {
			ActionSetContributionItem asci = (ActionSetContributionItem) item;
			IContributionItem subitem = asci.getInnerItem();
			return getDescription(subitem);
		}
		return null;
	}

	private static String getParamID(DisplayItem object) {
