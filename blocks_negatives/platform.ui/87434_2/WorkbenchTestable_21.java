            Runnable runnable = new Runnable() {
                @Override
				public void run() {
						if (WorkbenchTestable.this.workbench instanceof Workbench) {
							((Workbench) WorkbenchTestable.this.workbench).setEnableAutoSave(false);
						}
						Job.getJobManager().cancel(Workbench.WORKBENCH_AUTO_SAVE_JOB);
