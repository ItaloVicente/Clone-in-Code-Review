
						if (spec.line >= 1) {
							try {
								Object documentProvider = invoke(openEditor, "getDocumentProvider"); //$NON-NLS-1$

								Object editorInput = invoke(openEditor, "getEditorInput"); //$NON-NLS-1$

								Object document = invoke(documentProvider, "getDocument", new Class[] { Object.class }, //$NON-NLS-1$
										new Object[] { editorInput });

								int numberOfLines = (Integer) invoke(document, "getNumberOfLines"); //$NON-NLS-1$
								if (spec.line > numberOfLines) {
									spec.line = numberOfLines;
								}
								int lineLength = (Integer) invoke(document, "getLineLength", new Class[] { int.class }, //$NON-NLS-1$
										new Object[] { spec.line - 1 });
								if (spec.column > lineLength) {
									spec.column = lineLength;
								}
								if (spec.column < 1) {
									spec.column = 1;
								}
								int offset = (Integer) invoke(document, "getLineOffset", new Class[] { int.class }, //$NON-NLS-1$
										new Object[] { (spec.line - 1) });
								offset += (spec.column - 1);

								invoke(openEditor, "selectAndReveal", new Class[] { int.class, int.class }, //$NON-NLS-1$
										new Object[] { offset, 0 });
							} catch (Exception e) {
							}
						}
