		node.getRepository().getConfig()
				.addChangeListener(new ConfigChangedListener() {
					public void onConfigChanged(ConfigChangedEvent event) {
						lastObject = null;
						myPage.getSite().getShell().getDisplay().syncExec(new Runnable() {

							public void run() {
								myPage.setPropertySourceProvider(RepositoryPropertySourceProvider.this);
							}
						});
					}
				});
