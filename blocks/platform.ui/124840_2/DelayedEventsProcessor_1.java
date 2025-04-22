						Object document = invoke(documentProvider, "getDocument", new Class[] { Object.class }, //$NON-NLS-1$
								new Object[] { editorInput });

						int numberOfLines = (Integer) invoke(document, "getNumberOfLines"); //$NON-NLS-1$
						if (details.line > numberOfLines) {
							details.line = numberOfLines;
						}
						int lineLength = (Integer) invoke(document, "getLineLength", new Class[] { int.class }, //$NON-NLS-1$
								new Object[] { details.line - 1 });
						if (details.column > lineLength) {
							details.column = lineLength;
						}
						if (details.column < 1) {
							details.column = 1;
