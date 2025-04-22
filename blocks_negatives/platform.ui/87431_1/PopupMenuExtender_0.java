				workbench.getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						final Workbench realWorkbench = (Workbench) workbench;
						runCleanUp(realWorkbench);
					}
