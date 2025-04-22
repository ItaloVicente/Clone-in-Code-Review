						Display.getDefault().syncExec(new Runnable() {
							@Override
							public void run() {
								try {
								openProjectReferences = promptToOpenWithReferences();
								} catch (OperationCanceledException e) {
									canceled = true;
								}
								hasPrompted = true;
