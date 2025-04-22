	private class IntegerEditor extends IntegerFieldEditor {
		public IntegerEditor(String name, String labelText, Composite parent, int min, int max) {
	    	super(name, labelText, parent);
	    	setValidRange(min, max);
		}

		@Override
		protected void valueChanged() {
			super.valueChanged();
			if (longEventThreshold.isValid() &&
					sampleInterval.checkValue() && initialSampleDelay.checkValue()) {
				deadlockThreshold.checkValue();
			}
