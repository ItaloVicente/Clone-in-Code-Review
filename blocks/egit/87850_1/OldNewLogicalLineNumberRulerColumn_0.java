	private final LineNumberRulerColumn plainLines = new LineNumberRulerColumn() {

		@Override
		public Control createControl(CompositeRuler parentRuler,
				Composite parentControl) {
			return addMenuListener(
					super.createControl(parentRuler, parentControl));
		}
	};
