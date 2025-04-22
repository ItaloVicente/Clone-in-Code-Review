	/**
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

