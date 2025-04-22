			} else if (node != null) {
				ITypedElement element = node.getLeft();
				IResource resource = getResource(element);
				if (resource instanceof IFile && resource.exists()) {
					return getShowInSource(new ShowInContext(this,
							new StructuredSelection(resource)));
