	@Override
	public void refresh() {
		setDocument(getDocument(), getAnnotationModel());
	}

	@Override
	protected Layout createLayout() {
		return new FixedRulerLayout(GAP_SIZE_1);
	}

	private class FixedRulerLayout extends RulerLayout {

		public FixedRulerLayout(int gap) {
			super(gap);
		}

		@Override
		protected void layout(Composite composite, boolean flushCache) {
			Rectangle bounds = composite.getBounds();
			if (bounds.width == 0 || bounds.height == 0) {
				return;
			}
			super.layout(composite, flushCache);
		}
	}

