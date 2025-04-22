					format = fromString((String) event.getNewValue());
					FormatInfo info = DATA.get(format);
					formatExplanation.setText(info.explanation);
					if (format == null) {
						dateFormat.getTextControl(getFieldEditorParent())
								.setEnabled(true);
						dateFormat.setStringValue(lastCustomValue);
					} else {
						dateFormat.getTextControl(getFieldEditorParent())
								.setEnabled(false);
						dateFormat.setStringValue(info.format);
						updatePreview(format);
					}
