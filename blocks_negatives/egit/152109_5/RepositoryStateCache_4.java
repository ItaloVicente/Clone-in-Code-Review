		private IEditorInput getEditorInput(IEclipseContext context) {
			Object part = context.get(ISources.ACTIVE_PART_NAME);
			if (!(part instanceof IEditorPart)) {
				return null;
			}
			Object object = context.get(ISources.ACTIVE_EDITOR_INPUT_NAME);
			Object editor = context.get(ISources.ACTIVE_EDITOR_NAME);
			if (editor instanceof MultiPageEditorPart) {
				Object nestedEditor = ((MultiPageEditorPart) editor)
						.getSelectedPage();
				if (nestedEditor instanceof IEditorPart) {
					object = ((IEditorPart) nestedEditor).getEditorInput();
				}
			}
			if (!(object instanceof IEditorInput)
					&& (editor instanceof IEditorPart)) {
				object = ((IEditorPart) editor).getEditorInput();
			}
			if (object instanceof IEditorInput) {
				return (IEditorInput) object;
			}
			return null;
		}
