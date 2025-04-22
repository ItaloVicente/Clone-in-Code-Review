			if (element instanceof ContainerNode) {
				ContainerNode containerNode = (ContainerNode) element;
				IContainer resource = containerNode.getResource();
				if (resource != null && resource.isAccessible())
					return workbenchLabelProvider.getImage(resource);
				else
					return FOLDER_IMAGE;
			}
			FileNode fileNode = (FileNode) element;
			Type type = fileNode.getType();
