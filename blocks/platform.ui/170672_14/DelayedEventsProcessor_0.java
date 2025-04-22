								Bundle textEditorBundle = Platform.getBundle(TEXTEDITOR_BUNDLE_NAME);
								if (textEditorBundle != null) {
									Class<?> textEditorClass = textEditorBundle.loadClass(TEXTEDITOR_CLASS_NAME);
									if (textEditorClass != null) {
										Object textEditor = invoke(openEditor, "getAdapter", //$NON-NLS-1$
												new Class[] { Class.class }, new Object[] { textEditorClass });
										if (textEditor != null) {
											openEditor = (IEditorPart) textEditor;
										}
									}
								}

