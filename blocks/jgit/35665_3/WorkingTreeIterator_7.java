
	private class EmptyWorkingTreeIterator extends EmptyTreeIterator implements
			AttributeNodeProvider {

		public EmptyWorkingTreeIterator(WorkingTreeIterator p) {
			super(p);
		}

		public EmptyWorkingTreeIterator(EmptyTreeIterator p) {
			super(p);
		}

		public AttributesNode getInfoAttributesNode() throws IOException {
			return ((WorkingTreeIterator) parent).getInfoAttributesNode();
		}

		public AttributesNode getGlobalAttributesNode() throws IOException {
			return ((WorkingTreeIterator) parent).getGlobalAttributesNode();
		}

		@Override
		public EmptyTreeIterator createEmptyTreeIterator() {
			return new EmptyWorkingTreeIterator(this);
		}

	}

