						valueChanged();
					}
				});
				break;
			default:
				Assert.isTrue(false, "Unknown validate strategy");//$NON-NLS-1$
			}
			textField.addDisposeListener(event -> textField = null);
			if (textLimit > 0) {//Only set limits above 0 - see SWT spec
				textField.setTextLimit(textLimit);
			}
		} else {
			checkParent(textField, parent);
		}
		return textField;
	}

	public boolean isEmptyStringAllowed() {
		return emptyStringAllowed;
	}

	@Override
