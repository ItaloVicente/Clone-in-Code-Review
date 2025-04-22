	private class MyContentProvider implements IStructuredContentProvider {

		@Override
		public Object[] getElements(Object inputElement) {
			return (MyModel[]) inputElement;
		}

		@Override
		public void dispose() {

		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

		}

	}
