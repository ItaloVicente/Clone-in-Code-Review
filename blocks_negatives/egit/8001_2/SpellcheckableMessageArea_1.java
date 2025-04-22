			if (hardWrapModifyListener == null) {
				final StyledText textWidget = getTextWidget();

				hardWrapModifyListener = new ModifyListener() {

					private boolean active = true;

					public void modifyText(ModifyEvent e) {
						if (!active)
							return;
						String lineDelimiter = textWidget.getLineDelimiter();
						List<WrapEdit> wrapEdits = calculateWrapEdits(
								textWidget.getText(), MAX_LINE_WIDTH,
								lineDelimiter);
						active = false;
						textWidget.setRedraw(false);
						try {
							for (WrapEdit wrapEdit : wrapEdits)
								textWidget.replaceTextRange(wrapEdit.getStart(), wrapEdit.getLength(),
										wrapEdit.getReplacement());
						} finally {
							textWidget.setRedraw(true);
							active = true;
