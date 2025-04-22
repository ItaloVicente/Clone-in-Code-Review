	private static class EditAction extends Action {

		private RepositoryPropertySource source;

		public EditAction(String text, ImageDescriptor image,
				RepositoryPropertySource source) {
			super(text, image);
			this.source = source;
		}

		public EditAction setSource(RepositoryPropertySource source) {
			this.source = source;
			return this;
		}

		@Override
		public String getId() {
			return EDITACTIONID;
		}

		@Override
		public void run() {
			final StoredConfig config;

			DisplayMode mode = source.getCurrentMode();
			switch (mode) {
			case EFFECTIVE:
				return;
			case SYSTEM:
				config = source.systemConfig;
				break;
			case USER:
				config = source.userHomeConfig;
				break;
			case REPO:
				config = source.repositoryConfig;
				break;
			default:
				return;
			}

			new EditDialog(source.myPage.getSite().getShell(),
					(FileBasedConfig) config, mode.getText()).open();
			source.myPage.refresh();
		}

		@Override
		public int getStyle() {
			return IAction.AS_PUSH_BUTTON;
		}

	}

