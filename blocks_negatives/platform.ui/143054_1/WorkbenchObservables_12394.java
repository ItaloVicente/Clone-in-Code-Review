			private final IPropertyListener listener = new IPropertyListener() {
				@Override
				public void propertyChanged(Object source, int propId) {
					if (propId == IWorkbenchPartConstants.PROP_INPUT) {
						protectedSetValue(editor.getEditorInput());
					}
