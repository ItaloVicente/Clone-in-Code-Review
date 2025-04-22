
	private static class HoverCreators {

		private final IInformationControlCreator nonStickyCreator;

		private final IInformationControlCreator stickyCreator;

		private Point hoverSize;

		HoverCreators(IVerticalRulerInfo rulerInfo) {
			this.nonStickyCreator = parentShell -> new BlameInformationControl(
					parentShell, rulerInfo) {

				@Override
				public Point computeSizeHint() {
					Point size = super.computeSizeHint();
					hoverSize = Geometry.copy(size);
					return size;
				}
			};
			this.stickyCreator = parentShell -> new BlameInformationControl(
					parentShell, rulerInfo, true) {

				@Override
				public Point computeSizeConstraints(int widthInChars,
						int heightInChars) {
					Point size = super.computeSizeConstraints(widthInChars,
							heightInChars);
					if (hoverSize != null) {
						size = Geometry.max(size, hoverSize);
					}
					return size;
				}
			};
		}

		public IInformationControlCreator hoverCreator() {
			return nonStickyCreator;
		}

		public IInformationControlCreator stickyHoverCreator() {
			return stickyCreator;
		}
	}
