    @Override
	public final String getText(Object element) {
        if (element instanceof IPerspectiveDescriptor) {
            IPerspectiveDescriptor desc = (IPerspectiveDescriptor) element;
            String label = desc.getLabel();
            if (markDefault) {
                String def = PlatformUI.getWorkbench().getPerspectiveRegistry()
                        .getDefaultPerspective();
                if (desc.getId().equals(def)) {
                    label = NLS.bind(WorkbenchMessages.PerspectivesPreference_defaultLabel, label );
                }
            }
            return label;
        }
        return WorkbenchMessages.PerspectiveLabelProvider_unknown;
    }
