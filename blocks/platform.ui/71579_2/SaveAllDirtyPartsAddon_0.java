			} else if (autoSaveJob.getState() == Job.SLEEPING) {
				if (event.type == SWT.Show && event.widget instanceof Menu) {
					autoSaveJob.cancel();
				} else {
					autoSaveJob.cancel();
					autoSaveJob.schedule(autoSaveInterval);
				}
