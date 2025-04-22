			};
			localSaveHandler.logger = logger;
			windowContext.set(ISaveHandler.class, localSaveHandler);

			windowContext.set(IWorkbenchWindow.class.getName(), this);
			windowContext.set(IPageService.class, this);
			windowContext.set(IPartService.class, partService);
