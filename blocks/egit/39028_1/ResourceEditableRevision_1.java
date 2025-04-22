			ISchedulingRule rule = Job.getJobManager().currentRule();
			boolean fork = true;
			if (rule instanceof IResource) {
				if (file.exists() && ((IResource) rule).isConflicting(file))
					fork = false;
			}
			runnableContext.run(fork, false, new IRunnableWithProgress() {
