	private class SizedComposite extends Composite {

		int height;

		public SizedComposite(Composite parent, int style, int height) {
			super(parent, style);
			this.height = height;
		}

		@Override
		public Point computeSize(int wHint, int hHint, boolean changed) {
			return new Point( 20, height);
		}
	}

