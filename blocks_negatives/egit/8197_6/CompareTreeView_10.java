		}
	}

	private final class WorkbenchTreeLabelProvider extends
			WorkbenchLabelProvider {
		@Override
		protected ImageDescriptor decorateImage(ImageDescriptor baseImage,
				Object element) {
			if (!(element instanceof IFile)) {
				return super.decorateImage(baseImage, element);
			}
			IPath elementPath = new Path(repositoryMapping
					.getRepoRelativePath((IFile) element));
			if (addedPaths.contains(elementPath)) {
				return UIIcons.ELCL16_ADD;
			}
			if (deletedPaths.contains(elementPath)) {
				return UIIcons.ELCL16_DELETE;
			}
			if (equalContentPaths.contains(elementPath)) {
				ImageDescriptor imageDesc = super.decorateImage(baseImage,
						element);
				return ImageDescriptor.createWithFlags(imageDesc,
						SWT.IMAGE_DISABLE);
			}
			return super.decorateImage(baseImage, element);
