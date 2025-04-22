				.addConfigChangedListener(new ConfigChangedListener() {
					@Override
					public void onConfigChanged(ConfigChangedEvent event) {
						lastObject = null;
						myPage.getSite().getShell().getDisplay().asyncExec(new Runnable() {

							@Override
							public void run() {
								myPage.setPropertySourceProvider(RepositoryPropertySourceProvider.this);
							}
						});
					}
				});
