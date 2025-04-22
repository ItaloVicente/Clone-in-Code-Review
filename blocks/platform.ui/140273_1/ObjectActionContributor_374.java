		IStructuredSelection ssel = (IStructuredSelection) sel;

		if (canAdapt()) {
			IStructuredSelection newSelection = LegacyResourceSupport.adaptSelection(ssel, getObjectClass());
			if (newSelection.size() != ssel.size()) {
				if (Policy.DEBUG_CONTRIBUTIONS) {
					WorkbenchPlugin.log("Error adapting selection to " + getObjectClass() + //$NON-NLS-1$
							". Contribution " + getID(config) + " is being ignored"); //$NON-NLS-1$ //$NON-NLS-2$
				}
				return false;
