					UIIcons.EDITCONFIG) {
				@Override
				public String getId() {
					return EDITACTIONID;
				}

				@Override
				public void run() {

					final StoredConfig config;

					switch (getCurrentMode()) {
					case EFFECTIVE:
						return;
					case SYSTEM:
						config = systemConfig;
						break;
					case USER:
						config = userHomeConfig;
						break;
					case REPO:
						config = repositoryConfig;
						break;
					default:
						return;
					}

					new EditDialog(myPage.getSite().getShell(),
							(FileBasedConfig) config, getCurrentMode()
									.getText()).open();
					myPage.refresh();
				}

				@Override
				public int getStyle() {
					return IAction.AS_PUSH_BUTTON;
				}
			});
