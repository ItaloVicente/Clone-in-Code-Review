			}
		};
		IProgressService service = PlatformUI.getWorkbench().getProgressService();
		try {
			service.run(false, false, runnable);
		} catch (InterruptedException e) {
		} catch (InvocationTargetException e) {
			handle(e);
			return false;
		}
		return true;
	}
