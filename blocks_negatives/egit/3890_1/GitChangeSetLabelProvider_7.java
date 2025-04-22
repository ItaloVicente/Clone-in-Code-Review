	private static class DelegateLabelProvider extends LabelProvider {

		private final ResourceManager fImageCache = new LocalResourceManager(
				JFaceResources.getResources());

		public String getText(Object element) {
			if (element instanceof GitModelObject)
				return ((GitModelObject) element).getName();

			return null;
		}

		public Image getImage(Object element) {
			if (element instanceof GitModelBlob) {
				Object adapter = ((GitModelBlob) element)
						.getAdapter(IResource.class);
				return workbenchLabelProvider.getImage(adapter);
			}

			if (element instanceof GitModelTree) {
				Object adapter = ((GitModelTree) element)
						.getAdapter(IResource.class);
				return workbenchLabelProvider.getImage(adapter);
			}

			if (element instanceof GitModelCommit
					|| element instanceof GitModelCache
					|| element instanceof GitModelWorkingTree)
				return fImageCache.createImage(UIIcons.CHANGESET);

			if (element instanceof GitModelRepository)
				return fImageCache.createImage(UIIcons.REPOSITORY);

			return super.getImage(element);
		}

		@Override
		public void dispose() {
			fImageCache.dispose();
			super.dispose();
		}

	}

