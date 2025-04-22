			@Override
			public void run() {
				ConfigureKeysDialog dlg = new ConfigureKeysDialog(myPage
						.getSite().getShell(), getConfiguredKeys());
				if (dlg.open() == Window.OK)
					try {
						setConfiguredKeys(dlg.getActiveKeys());
						myPage.refresh();
					} catch (IOException e) {
						showExceptionMessage(e);
					}

			}
