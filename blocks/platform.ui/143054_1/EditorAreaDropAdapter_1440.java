			IEditorDescriptor editorDesc = null;
			try {
				String editorID = (String) marker
						.getAttribute(IDE.EDITOR_ID_ATTR);
				if (editorID != null) {
					IEditorRegistry editorReg = PlatformUI.getWorkbench()
							.getEditorRegistry();
					editorDesc = editorReg.findEditor(editorID);
				}
			} catch (CoreException e) {
			}
