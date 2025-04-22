						textWidget.setRedraw(false);
						try {
							for (WrapEdit wrapEdit : wrapEdits)
								textWidget.replaceTextRange(wrapEdit.getStart(), wrapEdit.getLength(),
										wrapEdit.getReplacement());
						} finally {
							textWidget.setRedraw(true);
							active = true;
						}
