				}
			});
		} else if (result.isOK()) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					Shell shell = PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getShell();
					new PullResultDialog(shell, repository, pull.getResult())
							.open();
				}
			});
		}
	}

	public ISchedulingRule getSchedulingRule() {
		return ResourcesPlugin.getWorkspace().getRoot();
