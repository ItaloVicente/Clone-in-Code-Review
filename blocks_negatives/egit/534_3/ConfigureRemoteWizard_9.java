		}
		if (page == getPages()[2]) {
			SelectRemoteNamePage srp = (SelectRemoteNamePage) getPages()[0];
			if (srp.configurePush.getSelection()) {
				return getPages()[3];
			} else {
				return null;
			}
