		getWorkbenchProgressService().showInDialog(shell, job);
	}

	private IProgressService getWorkbenchProgressService() {
		return site.getWorkbenchWindow().getWorkbench().getProgressService();
	}

	@Override
	public void run(boolean fork, boolean cancelable, IRunnableWithProgress runnable)
			throws InvocationTargetException, InterruptedException {
		getWorkbenchProgressService().run(fork, cancelable, runnable);
	}

	@Override
	public void runInUI(IRunnableContext context, IRunnableWithProgress runnable, ISchedulingRule rule)
			throws InvocationTargetException, InterruptedException {
		getWorkbenchProgressService().runInUI(context, runnable, rule);
	}

	@Override
