	private void setMessage(String text) {
		if (text == null) {
			message.setImage(null);
			message.setText(
					UIText.GitHistoryPage_filterRefDialog_dialogMessage);
		} else {
			message.setImage(PlatformUI.getWorkbench().getSharedImages()
					.getImage(ISharedImages.IMG_OBJS_ERROR_TSK));
			message.setText(text);
		}
	}

