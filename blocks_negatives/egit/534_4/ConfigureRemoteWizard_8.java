		if (page == getPages()[1] || page == getPages()[3]) {
			RefSpecPage next = (RefSpecPage) getPages()[2];
			next
					.setConfigName(((SelectRemoteNamePage) getPages()[0]).remoteName
							.getText());
			next = (RefSpecPage) getPages()[4];
			next
					.setConfigName(((SelectRemoteNamePage) getPages()[0]).remoteName
							.getText());
