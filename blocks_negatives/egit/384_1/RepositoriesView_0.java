	enum RepositoryTreeNodeType {

		REPO(Activator.ICON_REPOSITORY), REF(PlatformUI.getWorkbench()
				.getSharedImages().getImage(ISharedImages.IMG_OBJ_FOLDER)), PROJ(
				PlatformUI.getWorkbench().getSharedImages().getImage(
						SharedImages.IMG_OBJ_PROJECT_CLOSED)), BRANCHES(
				Activator.ICON_BRANCHES), PROJECTS(PlatformUI.getWorkbench()
				.getSharedImages().getImage(ISharedImages.IMG_OBJ_FOLDER));

		private final Image myImage;

		private RepositoryTreeNodeType(String iconName) {

			if (iconName != null) {
				myImage = Activator.getDefault().getImageRegistry().get(
						iconName);
			} else {
				myImage = null;
			}

		}

		private RepositoryTreeNodeType(Image icon) {
			myImage = icon;

		}

		public Image getIcon() {
			return myImage;
		}

	}

	private static final class RepositoryTreeNode<T> {

		private final Repository myRepository;

		private final T myObject;

		private final RepositoryTreeNodeType myType;

		private final RepositoryTreeNode myParent;

		private String branch;

		public RepositoryTreeNode(RepositoryTreeNode parent,
				RepositoryTreeNodeType type, Repository repository, T treeObject) {
			myParent = parent;
			myRepository = repository;
			myType = type;
			myObject = treeObject;
		}

		@SuppressWarnings("unchecked")
		private RepositoryTreeNode<Repository> getRepositoryNode() {
			if (myType == RepositoryTreeNodeType.REPO) {
				return (RepositoryTreeNode<Repository>) this;
			} else {
				return getParent().getRepositoryNode();
			}
		}

		/**
		 * We keep this cached in the repository node to avoid repeated lookups
		 *
		 * @return the full branch
		 * @throws IOException
		 */
		public String getBranch() throws IOException {
			if (myType != RepositoryTreeNodeType.REPO) {
				return getRepositoryNode().getBranch();
			}
			if (branch == null) {
				branch = getRepository().getBranch();
			}
			return branch;
		}

		public RepositoryTreeNode getParent() {
			return myParent;
		}

		public RepositoryTreeNodeType getType() {
			return myType;
		}

		public Repository getRepository() {
			return myRepository;
		}

		public T getObject() {
			return myObject;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			switch (myType) {
			case REPO:
			case PROJECTS:
			case BRANCHES:
				result = prime
						* result
						+ ((myObject == null) ? 0 : ((Repository) myObject)
								.getDirectory().hashCode());
				break;
			case REF:
				result = prime
						* result
						+ ((myObject == null) ? 0 : ((Ref) myObject).getName()
								.hashCode());
				break;
			case PROJ:
				result = prime
						* result
						+ ((myObject == null) ? 0 : ((File) myObject).getPath()
								.hashCode());
				break;

			default:
				break;
			}

			result = prime * result
					+ ((myParent == null) ? 0 : myParent.hashCode());
			result = prime
					* result
					+ ((myRepository == null) ? 0 : myRepository.getDirectory()
							.hashCode());
			result = prime * result
					+ ((myType == null) ? 0 : myType.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;

			RepositoryTreeNode other = (RepositoryTreeNode) obj;

			if (myType == null) {
				if (other.myType != null)
					return false;
			} else if (!myType.equals(other.myType))
				return false;
			if (myParent == null) {
				if (other.myParent != null)
					return false;
			} else if (!myParent.equals(other.myParent))
				return false;
			if (myRepository == null) {
				if (other.myRepository != null)
					return false;
			} else if (!myRepository.getDirectory().equals(
					other.myRepository.getDirectory()))
				return false;
			if (myObject == null) {
				if (other.myObject != null)
					return false;
			} else if (!checkObjectsEqual(other.myObject))
				return false;

			return true;
		}

		private boolean checkObjectsEqual(Object otherObject) {
			switch (myType) {
			case REPO:
			case PROJECTS:
			case BRANCHES:
				return ((Repository) myObject).getDirectory().equals(
						((Repository) otherObject).getDirectory());
			case REF:
				return ((Ref) myObject).getName().equals(
						((Ref) otherObject).getName());
			case PROJ:
				return ((File) myObject).getPath().equals(
						((File) otherObject).getPath());
			default:
				return false;
			}
		}
	}

