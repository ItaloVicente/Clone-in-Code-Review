				FileRevisionEditorInput editorInput = new FileRevisionEditorInput(
						fileRevision, storage);
				editorPart = EgitUiEditorUtils.openEditor(page, editorInput);
				if (editorPart instanceof MultiPageEditorPart) {
					MultiPageEditorPart multiEditor = (MultiPageEditorPart) editorPart;
					for (IEditorPart part : multiEditor
							.findEditors(editorInput)) {
						if (part instanceof AbstractDecoratedTextEditor) {
							multiEditor.setActiveEditor(part);
							editorPart = part;
							break;
						}
					}
				}
