								Class<?> textEditorClass = Class.forName("org.eclipse.ui.texteditor.ITextEditor", true, //$NON-NLS-1$
										openEditor.getClass().getClassLoader());
								Object textEditor = invoke(openEditor, "getAdapter", new Class[] { Class.class }, //$NON-NLS-1$
										new Object[] { textEditorClass });
								if (textEditor != null && textEditor instanceof IEditorPart) {
									openEditor = (IEditorPart) textEditor;
								}

