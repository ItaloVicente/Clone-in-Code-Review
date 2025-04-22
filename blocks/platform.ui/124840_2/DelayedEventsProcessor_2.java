						int offset = (Integer) invoke(document, "getLineOffset", new Class[] { int.class }, //$NON-NLS-1$
								new Object[] { (details.line - 1) });
						offset += (details.column - 1);

						invoke(openEditor, "selectAndReveal", new Class[] { int.class, int.class }, //$NON-NLS-1$
								new Object[] { offset, 0 });
					} catch (Exception e) {
