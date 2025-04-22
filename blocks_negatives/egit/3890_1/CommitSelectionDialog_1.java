	private static class RepositoryLabelProvider extends LabelProvider {

		private Image repositoryImage = UIIcons.REPOSITORY.createImage();

		public void dispose() {
			this.repositoryImage.dispose();
			super.dispose();
		}

		public Image getImage(Object element) {
			return this.repositoryImage;
		}

		public String getText(Object element) {
			if (element instanceof RepositoryCommit)
				return ((RepositoryCommit) element).getRepositoryName();
			else if (element != null)
				return element.toString();
			else
		}

	}

