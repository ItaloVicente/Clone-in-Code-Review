			Expression enabledWhen = null;
			final IConfigurationElement[] ewChildren = configElement
					.getChildren(IWorkbenchRegistryConstants.TAG_ENABLED_WHEN);
			if (ewChildren.length > 0) {
				final IConfigurationElement[] subChildren = ewChildren[0].getChildren();
				if (subChildren.length != 1) {
					Activator.trace(Policy.DEBUG_CMDS,
							"Incorrect enableWhen element " + commandId, null); //$NON-NLS-1$
					continue;
				}
				final ElementHandler elementHandler = ElementHandler.getDefault();
				final ExpressionConverter converter = ExpressionConverter.getDefault();
				try {
					enabledWhen = elementHandler.create(converter, subChildren[0]);
				} catch (CoreException e) {
					Activator.trace(Policy.DEBUG_CMDS,
							"Incorrect enableWhen element " + commandId, e); //$NON-NLS-1$
				}
