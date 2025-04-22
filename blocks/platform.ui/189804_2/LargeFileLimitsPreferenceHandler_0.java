			try {
				IPath inputPath = pathEditorInput.getPath();
				return getEditorForPath(inputPath);
			} catch (Exception e) {
				Status warning = Status
						.warning("Exception occurred while checking large file editor for " + editorInput, e); //$NON-NLS-1$
				WorkbenchPlugin.log(warning);
			}
