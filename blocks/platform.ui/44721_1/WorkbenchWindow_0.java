
			if (Boolean.valueOf(getModel().getPersistedState().get(PERSISTED_STATE_RESTORED))) {
				SafeRunnable.run(new SafeRunnable() {

					@Override
					public void run() throws Exception {
						getWindowAdvisor().postWindowRestore();
					}
				});
			} else {
				getModel().getPersistedState().put(PERSISTED_STATE_RESTORED, Boolean.TRUE.toString());
			}

