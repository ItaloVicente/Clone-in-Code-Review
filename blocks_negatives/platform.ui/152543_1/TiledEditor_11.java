			e.addPropertyListener(new IPropertyListener() {
				@Override
				public void propertyChanged(Object source, int property) {
					if (property == IEditorPart.PROP_DIRTY
							|| property == IWorkbenchPart.PROP_TITLE) {
						if (source instanceof IEditorPart) {
							updateInnerEditorTitle((IEditorPart) source,
									innerEditorTitle[index]);
						}
