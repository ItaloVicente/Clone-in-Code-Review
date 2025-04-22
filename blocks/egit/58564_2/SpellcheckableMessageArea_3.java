						if (e.widget == textWidget) {
							int footerOffset = CommonUtils
									.getFooterOffset(textWidget.getText());
							if (footerOffset >= 0
									&& e.lineOffset >= footerOffset) {
								return;
							}
						}
