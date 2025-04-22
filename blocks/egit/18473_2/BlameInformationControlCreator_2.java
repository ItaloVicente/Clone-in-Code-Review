	private static class EnrichedCreator extends
			AbstractReusableInformationControlCreator {

		private BlameInformationControl hoverInformationControl;

		@Override
		protected IInformationControl doCreateInformationControl(Shell parent) {
			return new BlameInformationControl(parent, hoverInformationControl);
		}
	}
