
		if (!createMode) {
			return super.getNextPage(page);
		}
		if (page instanceof SelectRemoteNamePage) {
			SelectRemoteNamePage srp = (SelectRemoteNamePage) page;
			if (srp.configureFetch.getSelection()) {
				return getPages()[1];
			}
			if (srp.configurePush.getSelection()) {
				return getPages()[3];
			}
