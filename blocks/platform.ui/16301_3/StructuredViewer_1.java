	void buildLabel(ViewerLabel updateLabel, Object element,
			IViewerLabelProvider labelProvider) {

		labelProvider.updateLabel(updateLabel, element);

		colorAndFontCollector.setUsedDecorators();

		if (updateLabel.hasNewBackground()) {
			colorAndFontCollector.setBackground(updateLabel.getBackground());
		}

		if (updateLabel.hasNewForeground()) {
			colorAndFontCollector.setForeground(updateLabel.getForeground());
		}

		if (updateLabel.hasNewFont()) {
			colorAndFontCollector.setFont(updateLabel.getFont());
		}
