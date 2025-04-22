	 * Used to render the "right" side of a workbench compare
	 */
	private final class GitWorkbenchLabelProvider extends LabelProvider
			implements IColorProvider, IFontProvider {
		WorkbenchLabelProvider myProvider = new WorkbenchLabelProvider();

		@Override
		public Image getImage(Object element) {
			Image superImage = myProvider.getImage(element);
			if (superImage == null)
				return DELETED;
			if (element instanceof IFile) {
				if (equalIds.contains(new Path(repositoryMapping
						.getRepoRelativePath((IFile) element)))) {
					return SAME_CONTENT;
				}
			}
			return superImage;
		}

		@Override
		public String getText(Object element) {
			if (element instanceof GitFileRevision) {
				return ((GitFileRevision) element).getName();
			}
			return myProvider.getText(element);
		}

		public Color getBackground(Object element) {
			return myProvider.getBackground(element);
		}

		public Color getForeground(Object element) {
			return myProvider.getForeground(element);
		}

		public Font getFont(Object element) {
			return myProvider.getFont(element);
		}
	}

	/**
	 * Used to render the left tree in case we have no workspace
	 */
	private abstract class AbstractPathNodeContentProvider extends
			ArrayContentProvider implements ITreeContentProvider {
		@Override
		public Object[] getElements(Object inputElement) {
			IResource[] resources = (IResource[]) input;
			PathNode[] nodes = new PathNode[resources.length];
			for (int i = 0; i < resources.length; i++) {
				IResource resource = resources[i];
				if (resource instanceof IFile) {
					IPath path = new Path(repositoryMapping
							.getRepoRelativePath(resource));
					Type type;
					if (leftOnly.contains(path))
						type = Type.FILE_ADDED;
					else if (equalIds.contains(path))
						type = Type.FILE_BOTH_SIDES_SAME;
					else
						type = Type.FILE_BOTH_SIDES_DIFFER;
					nodes[i] = new PathNode(path, type);
				} else
					nodes[i] = new PathNode(new Path(repositoryMapping
							.getRepoRelativePath(resource)), Type.FOLDER);
			}
			return nodes;
		}

		public Object getParent(Object element) {
			if (!(element instanceof PathNode))
				return null;
			IPath currentPath = ((PathNode) element).path;
			if (currentPath.segmentCount() > 0)
				return currentPath.removeLastSegments(1);
			return null;
		}
	}

	/**
	 * Used to render the left tree in case we have no workspace
