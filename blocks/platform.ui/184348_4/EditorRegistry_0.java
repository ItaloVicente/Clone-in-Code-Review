			IEditorDescriptor editor = mapIDtoOSEditors.get(id);
			if (editor == null) {
				WorkbenchPlugin.getDefault().getLog()
						.log(StatusUtil.newStatus(IStatus.WARNING, "Editor not found:'" + id //$NON-NLS-1$
								+ "'. Please avoid non-existing editor request at least during startup.", null)); //$NON-NLS-1$
			}
