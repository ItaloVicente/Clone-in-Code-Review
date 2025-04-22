			final ElementHandler elementHandler = ElementHandler.getDefault();
			final ExpressionConverter converter = ExpressionConverter.getDefault();
			try {
				activeWhen = elementHandler.create(converter, subChildren[0]);
			} catch (CoreException e) {
				Activator.trace(Policy.DEBUG_CMDS, "Incorrect activeWhen element " + commandId, e); //$NON-NLS-1$
