
		@Override
		public boolean isContainer() {
			return true;
		}

		@Override
		public ISchedulingRule getRule(Object object) {
			return null;
		}
	}

	private static class ErrorElement extends WorkbenchAdapter {

		@Override
		public String toString() {
			return UIText.ReflogView_ErrorOnLoad;
		}
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
