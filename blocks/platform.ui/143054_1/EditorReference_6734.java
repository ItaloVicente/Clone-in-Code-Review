					factoryId = null;
					return;
				}
				IEditorRegistry registry = getPage().getWorkbenchWindow().getWorkbench().getEditorRegistry();
				descriptorId = createReadRoot.getString(IWorkbenchConstants.TAG_ID);
				this.descriptor = (EditorDescriptor) registry.findEditor(descriptorId);
