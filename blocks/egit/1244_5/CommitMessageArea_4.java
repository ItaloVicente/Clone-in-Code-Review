	private void configureHardWrap() {
		if (shouldHardWrap()) {
			if (hardWrapModifyListener == null) {
				final StyledText textWidget = getTextWidget();

				hardWrapModifyListener = new ModifyListener() {

					private boolean active = true;

					public void modifyText(ModifyEvent e) {
						if (!active) {
							return;
						}
						String lineDelimiter = textWidget.getLineDelimiter();
						List<WrapEdit> wrapEdits = calculateWrapEdits(textWidget.getText(), 70, lineDelimiter);
						active = false;
						for (WrapEdit wrapEdit : wrapEdits) {
							textWidget.replaceTextRange(wrapEdit.getStart(), wrapEdit.getLength(), lineDelimiter);
						}
						active = true;
					}
				};
				textWidget.addModifyListener(hardWrapModifyListener);
			}
		} else {
			if (hardWrapModifyListener != null) {
				getTextWidget().removeModifyListener(hardWrapModifyListener);
				hardWrapModifyListener = null;
			}
		}
	}

