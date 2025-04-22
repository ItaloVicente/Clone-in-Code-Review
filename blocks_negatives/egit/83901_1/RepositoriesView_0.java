			if (this.scheduledJob != null)
				try {
					this.scheduledJob.join();
				} catch (InterruptedException e) {
					Activator.handleError(e.getMessage(), e, false);
				}

