
			if (Boolean.valueOf(getModel().getPersistedState().get(PERSISTED_STATE_ISRESTORED))) {
				SafeRunnable.run(new SafeRunnable() {

					@Override
					public void run() throws Exception {
						getWindowAdvisor().postWindowRestore();
					}
				});
			} else {
				getModel().getPersistedState().put(PERSISTED_STATE_ISRESTORED, Boolean.TRUE.toString());
			}

