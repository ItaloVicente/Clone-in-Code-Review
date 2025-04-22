			CSSSWTImageHelper.storeDefaultImage(button);
			button.setImage(image);

		} else {
			try {
				if (control instanceof Control) {
					((Control) control).setBackgroundImage(image);
				}
			} catch (Throwable e) {
			}
