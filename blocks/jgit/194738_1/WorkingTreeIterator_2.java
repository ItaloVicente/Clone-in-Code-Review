	private static class IgnoreNodeWithParent extends IgnoreNode {

		private final IgnoreNode parent;

		IgnoreNodeWithParent(IgnoreNode parent) {
			this.parent = parent;
		}

		@Override
		public Boolean checkIgnored(String path
			Boolean result = super.checkIgnored(path
			if (result == null && parent != null) {
				return parent.checkIgnored(path
			}
			return result;
		}
	}

