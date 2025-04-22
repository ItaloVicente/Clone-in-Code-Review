					} else // editor already rendered, try to update its state
					if (curMemento != null && ee.getModel().getObject() instanceof CompatibilityEditor) {
						CompatibilityEditor ce = (CompatibilityEditor) ee.getModel().getObject();
						if (ce.getEditor() instanceof IPersistableEditor) {
							IPersistableEditor pe = (IPersistableEditor) ce.getEditor();

							IMemento editorMem = curMemento.getChild(IWorkbenchConstants.TAG_EDITOR_STATE);
							if (editorMem == null) {
								IMemento[] kids = curMemento.getChildren();
								if (kids.length == 2)
									editorMem = kids[1];
