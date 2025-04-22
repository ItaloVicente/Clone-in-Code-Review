		private Image getEditorImage(CommitItem item) {
			Image image = DEFAULT;
			String name = new Path(item.path).lastSegment();
			if (name != null) {
				ImageDescriptor descriptor = PlatformUI.getWorkbench()
						.getEditorRegistry().getImageDescriptor(name);
				image = (Image) this.resourceManager.get(descriptor);
			}
			return image;
		}

		private Image getDecoratedImage(Image base, ImageDescriptor decorator) {
			DecorationOverlayIcon decorated = new DecorationOverlayIcon(base,
					decorator, IDecoration.BOTTOM_RIGHT);
			return (Image) this.resourceManager.get(decorated);
		}

		public String getText(Object obj) {
			return ""; //$NON-NLS-1$
		}

		public Image getImage(Object element) {
			CommitItem item = (CommitItem) element;
			ImageDescriptor decorator = null;
			switch (item.status) {
			case UNTRACKED:
				decorator = UIIcons.OVR_UNTRACKED;
				break;
			case ADDED:
			case ADDED_INDEX_DIFF:
				decorator = UIIcons.OVR_STAGED_ADD;
				break;
			case REMOVED:
			case REMOVED_NOT_STAGED:
			case REMOVED_UNTRACKED:
				decorator = UIIcons.OVR_STAGED_REMOVE;
				break;
