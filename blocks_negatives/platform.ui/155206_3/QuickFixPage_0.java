
		new OpenAndLinkWithEditorHelper(markersTable) {

			{ setLinkWithEditor(false); }

			@Override
			protected void activate(ISelection selection) {
				open(selection, true);
			}

			/** Not supported*/
			@Override
			protected void linkToEditor(ISelection selection) {
			}

			@Override
			protected void open(ISelection selection, boolean activate) {
				if (selection.isEmpty())
					return;
				IMarker marker = (IMarker) ((IStructuredSelection) selection)
						.getFirstElement();
				if (marker != null && marker.getResource() instanceof IFile) {
					try {
						IDE.openEditor(site.getPage(), marker, activate);
					} catch (PartInitException e) {
						MarkerSupportInternalUtilities.showViewError(e);
					}
				}
			}
		};
