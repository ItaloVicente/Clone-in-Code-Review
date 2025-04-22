					if (input.getRight() == null) {
						final ITypedElement newEmptyRemote = new GitCompareFileRevisionEditorInput.EmptyTypedElement(
								resource.getName());
						((ResourceDiffCompareInput) input)
								.setRight(newEmptyRemote);
					}
