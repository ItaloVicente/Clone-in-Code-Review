			@Override
			public void asyncExec(final Runnable runnable) {
				runnable.run();
			}
		});
		ContextInjectionFactory.setDefault(appContext);
		ems = appContext.get(EModelService.class);
	}
