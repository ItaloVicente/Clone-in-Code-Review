			ResourcesPlugin.getWorkspace().getRoot().accept(resource -> {
				if (resource.getType() == IResource.FILE) {
					res.add(new QuickAccessElement() {
						@Override
						public String getLabel() {
							return labelProvider.getText(resource);
						}

						@Override
						public ImageDescriptor getImageDescriptor() {
							ImageData imageData = labelProvider.getImage(resource).getImageData();
							return ImageDescriptor.createFromImageDataProvider(zoom -> imageData);
						}

						@Override
						public String getId() {
							return resource.getFullPath().toString();
						}
