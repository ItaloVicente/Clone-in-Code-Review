		if (!VAL_FAST.equals(relationship) && relative == null) {
			logError(element,
					"Attribute '" + IWorkbenchRegistryConstants.ATT_RELATIVE //$NON-NLS-1$
							+ "' not defined.  This attribute is required when " //$NON-NLS-1$
							+ IWorkbenchRegistryConstants.ATT_RELATIONSHIP + "=\"" + relationship + "\"."); //$NON-NLS-1$ //$NON-NLS-2$
			return false;
		}
