			VerifyListener listener = (VerifyListener) text
					.getData("CSSModifyTextListener");
			if (hasTextTransform(value)) {
				if (listener == null) {
					listener = new VerifyListener() {
						@Override
						public void verifyText(VerifyEvent e) {
						}
					};
					text.addVerifyListener(listener);
					text.setData("CSSModifyTextListener", listener);
				}
			} else {
				if (listener != null) {
					text.removeVerifyListener(listener);
					text.setData("CSSModifyTextListener", null);
				}
			}
