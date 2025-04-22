			final ElementHandler elementHandler = ElementHandler.getDefault();
			final ExpressionConverter converter = ExpressionConverter.getDefault();
			try {
				enabledWhen = elementHandler.create(converter, subChildren[0]);
			} catch (CoreException e) {
				Activator.trace(Policy.DEBUG_CMDS, "Incorrect enableWhen element " + commandId, e); //$NON-NLS-1$
