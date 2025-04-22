			IEditorDescriptor editor = mapIDtoOSEditors.get(id);
			if (editor == null) {
				WorkbenchPlugin.getDefault().getLog()
						.log(StatusUtil.newStatus(IStatus.WARNING, "Editor descriptor for id '" + id + "' not found.", //$NON-NLS-1$ //$NON-NLS-2$
								new Exception("IEditorRegistry.findEditor(String) called for unknown id"))); //$NON-NLS-1$
			}
