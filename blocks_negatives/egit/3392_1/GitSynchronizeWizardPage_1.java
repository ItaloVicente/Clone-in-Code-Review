			@Override
			public Image getImage(Object element) {
				if (element instanceof Repository) {
					return repositoryImage;
				}
				return PlatformUI.getWorkbench().getSharedImages().getImage(
						IDE.SharedImages.IMG_OBJ_PROJECT);
