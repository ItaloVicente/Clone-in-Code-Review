				return WorkbenchPage.saveAll(saveables, confirm, false, true, WorkbenchWindow.this,
						WorkbenchWindow.this);
			}
		};
		localSaveHandler.logger = logger;
		windowContext.set(ISaveHandler.class, localSaveHandler);
