
	class NoRepositorySelectedLabelProvider extends LabelProvider {
		@Override
		public String getText(Object element) {
			if (element instanceof Repository) {

				Repository repository = (Repository) element;
				return repository.toString();
			}
			return super.getText(element);
		}
	}
