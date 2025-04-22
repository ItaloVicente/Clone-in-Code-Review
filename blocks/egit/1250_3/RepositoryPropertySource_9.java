				@Override
				public void run() {

					final StoredConfig config;

					switch (getCurrentMode()) {
					case EFFECTIVE:
						return;
					case USER:
						config = userHomeConfig;
						break;
					case REPO:
						config = repositoryConfig;
						break;
					default:
						return;
					}
