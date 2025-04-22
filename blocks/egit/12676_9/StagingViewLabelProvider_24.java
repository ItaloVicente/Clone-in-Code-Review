	public Image getImage(Object element) {

		if (element instanceof StagingFolderEntry) {
			StagingFolderEntry c = (StagingFolderEntry) element;
			if (c.getContainer() == null) {
				return PlatformUI.getWorkbench().getSharedImages()
						.getImage(ISharedImages.IMG_OBJ_FOLDER);
			}
			return workbenchLabelProvider
					.getImage(((StagingFolderEntry) element).getContainer());
		}

		StagingEntry c = (StagingEntry) element;
		DecorationResult decoration = new DecorationResult();
