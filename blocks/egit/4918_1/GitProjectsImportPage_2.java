		@Override
		public String getText(Object element) {
			if (element instanceof ProjectFolder)
				return ((ProjectFolder) element).getName();

			return super.getText(element);
		}

		@Override
		public Image getImage(Object element) {
			if (element instanceof ProjectFolder)
				return PlatformUI.getWorkbench().getSharedImages()
						.getImage(ISharedImages.IMG_OBJ_FOLDER);

			return super.getImage(element);
		}

