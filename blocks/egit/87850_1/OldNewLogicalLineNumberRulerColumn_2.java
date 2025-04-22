			DiffEntry.Side.NEW) {

		@Override
		public Control createControl(CompositeRuler parentRuler,
				Composite parentControl) {
			return addMenuListener(
					super.createControl(parentRuler, parentControl));
		}
	};
