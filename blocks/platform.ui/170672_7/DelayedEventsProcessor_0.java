								try {
									Class<?> textEditorClass = Class.forName("org.eclipse.ui.texteditor.ITextEditor", //$NON-NLS-1$
											true,
										openEditor.getClass().getClassLoader());
									if (textEditorClass != null) {
										Object textEditor = invoke(openEditor, "getAdapter", //$NON-NLS-1$
												new Class[] { Class.class }, new Object[] { textEditorClass });
										if (textEditor != null) {
											openEditor = (IEditorPart) textEditor;
										}
									}
								} catch (ClassNotFoundException e) {
								}

