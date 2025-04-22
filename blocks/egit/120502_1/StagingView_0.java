				if (configChangedListener != null) {
					configChangedListener.remove();
				}
				configChangedListener = repository.getListenerList()
						.addConfigChangedListener(
								event -> resetCommitMessageComponent());
