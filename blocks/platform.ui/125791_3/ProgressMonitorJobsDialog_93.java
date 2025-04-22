            	getUISynchronize().syncExec(new Runnable() {
                    @Override
					public void run() {
						 if (!ProgressManagerUtil.safeToOpen(ProgressMonitorJobsDialog.this,null)){
							  watchTicks();
							  return;
						 }

                        if (!alreadyClosed) {
							open();
						}
                    }
                });
