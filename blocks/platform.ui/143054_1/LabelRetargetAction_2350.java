		super.setActionHandler(handler);

		if (handler == null) {
			super.setText(defaultText);
			super.setToolTipText(defaultToolTipText);
		} else {
			String handlerText = handler.getText();
			if (handlerText == null || handlerText.length() == 0) {
				handlerText = defaultText;
			}
			super.setText(appendAccelerator(handlerText));
			super.setToolTipText(handler.getToolTipText());
		}
		updateImages(handler);
	}

	@Override
