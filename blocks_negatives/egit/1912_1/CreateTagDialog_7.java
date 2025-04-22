	static class TagInputList extends LabelProvider implements IWorkbenchAdapter {

		private final List<RevTag> tagList;

		public TagInputList(List<RevTag> tagList) {
			this.tagList = tagList;
		}

		public Object[] getChildren(Object o) {
			return tagList.toArray(new Object[] {});
		}

		public ImageDescriptor getImageDescriptor(Object object) {
			return null;
		}

		public String getLabel(Object o) {
			if (o instanceof RevTag)
				return ((RevTag) o).getTagName();

			return null;
		}

		public Object getParent(Object o) {
			return null;
		}

		public Object getAdapter(Class adapter) {
			if (adapter == IWorkbenchAdapter.class)
				return this;

			return null;
		}
	}

