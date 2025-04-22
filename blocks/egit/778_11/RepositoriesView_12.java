				Display.getDefault().asyncExec(new Runnable() {

					public void run() {
						selectReveal(new StructuredSelection(selNode));
					}
				});

			}
		} catch (RuntimeException rte) {
			Activator.handleError(rte.getMessage(), rte, false);
		}
	}

	public void refresh() {
		lastInputUpdate = -1l;
		scheduleRefresh();
	}

	private void scheduleRefresh() {
		if (scheduledJob != null && scheduledJob.getState() == Job.RUNNING) {
