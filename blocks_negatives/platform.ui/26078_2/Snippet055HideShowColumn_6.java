	private class MyContentProvider implements ITreeContentProvider {

		@Override
		public Object[] getElements(Object inputElement) {
			return ((MyModel) inputElement).child.toArray();
		}

		@Override
		public void dispose() {
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

		@Override
		public Object[] getChildren(Object parentElement) {
			return getElements(parentElement);
		}

		@Override
		public Object getParent(Object element) {
			if (element == null) {
				return null;
			}
			return ((MyModel) element).parent;
		}

		@Override
		public boolean hasChildren(Object element) {
			return ((MyModel) element).child.size() > 0;
		}

	}

	public class MyModel {
		public MyModel parent;

		public ArrayList child = new ArrayList();

		public int counter;

		public MyModel(int counter, MyModel parent) {
			this.parent = parent;
			this.counter = counter;
		}

		@Override
		public String toString() {
			String rv = "Item ";
			if (parent != null) {
				rv = parent.toString() + ".";
			}

			rv += counter;

			return rv;
		}
	}

