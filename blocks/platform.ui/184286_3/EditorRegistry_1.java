		IEditorDescriptor desc = mapIDtoInternalEditor.get(id);
		if (desc == null) {
			if (!"org.eclipse.e4.ui.compatibility.editor".equals(id)) { //$NON-NLS-1$
				desc = getIDtoOSEditors().get(id);
			}
		}
