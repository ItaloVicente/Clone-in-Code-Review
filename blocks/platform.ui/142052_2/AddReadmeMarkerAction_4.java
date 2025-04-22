		Map<String, Object> attributes = new HashMap<>(11);

		ITextSelection selection = (ITextSelection) textEditor.getSelectionProvider().getSelection();
		if (!selection.isEmpty()) {

			int start = selection.getOffset();
			int length = selection.getLength();

			if (length < 0) {
				length = -length;
				start -= length;
			}

			MarkerUtilities.setCharStart(attributes, start);
			MarkerUtilities.setCharEnd(attributes, start + length);

			int line = selection.getStartLine();
			MarkerUtilities.setLineNumber(attributes, line == -1 ? -1 : line + 1);

			for (int i = 0; i < customAttributes.length; i++) {
				attributes.put((String) customAttributes[i][0], customAttributes[i][1]);
			}

			MarkerUtilities.setMessage(attributes, message);
		}

		try {
			MarkerUtilities.createMarker(getResource(), attributes, MARKER_TYPE);
		} catch (CoreException x) {
			Bundle bundle = ReadmePlugin.getDefault().getBundle();
			Platform.getLog(bundle).log(x.getStatus());

			Shell shell = textEditor.getSite().getShell();
			String title = MessageUtil.getString("Add_readme_marker_error_title"); //$NON-NLS-1$
			String msg = MessageUtil.getString("Add_readme_marker_error_message"); //$NON-NLS-1$

			ErrorDialog.openError(shell, title, msg, x.getStatus());
		}
	}

	protected IResource getResource() {
		IEditorInput input = textEditor.getEditorInput();
		return Adapters.adapt(input, IResource.class);
	}
