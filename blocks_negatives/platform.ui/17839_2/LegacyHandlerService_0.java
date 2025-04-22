			Expression activeWhen = null;
			final IConfigurationElement[] awChildren = configElement
					.getChildren(IWorkbenchRegistryConstants.TAG_ACTIVE_WHEN);
			if (awChildren.length > 0) {
				final IConfigurationElement[] subChildren = awChildren[0].getChildren();
				if (subChildren.length != 1) {
					Activator.trace(Policy.DEBUG_CMDS,
							"Incorrect activeWhen element " + commandId, null); //$NON-NLS-1$
					continue;
				}
				final ElementHandler elementHandler = ElementHandler.getDefault();
				final ExpressionConverter converter = ExpressionConverter.getDefault();
				try {
					activeWhen = elementHandler.create(converter, subChildren[0]);
				} catch (CoreException e) {
					Activator.trace(Policy.DEBUG_CMDS,
							"Incorrect activeWhen element " + commandId, e); //$NON-NLS-1$
				}
