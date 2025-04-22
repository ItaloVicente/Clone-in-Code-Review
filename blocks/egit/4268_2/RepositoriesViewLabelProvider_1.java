		} else if (type == RepositoryTreeNodeType.FILE) {
			Object object = node.getObject();
			if (object instanceof File) {
				ImageDescriptor descriptor = PlatformUI.getWorkbench()
						.getEditorRegistry()
						.getImageDescriptor(((File) object).getName());
				return decorateImage((Image) resourceManager.get(descriptor),
						element);
			}
		}
		return decorateImage(node.getType().getIcon(), element);
