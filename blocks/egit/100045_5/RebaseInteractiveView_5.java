					if (input instanceof IFileEditorInput) {
						newInput(
								new StructuredSelection(
										((IFileEditorInput) input).getFile()),
								false);
					}
				} else {
					newInput(selection, false);
				}
