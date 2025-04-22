			ISchedulingRule rule = Job.getJobManager().currentRule();
			boolean fork = true;
			if (rule instanceof IResource) {
				IFile ourFile = ResourcesPlugin.getWorkspace().getRoot()
						.getFile(location);
				if (ourFile.exists()
						&& ((IResource) rule).isConflicting(ourFile))
					fork = false;
			}
			runnableContext.run(fork, false, new IRunnableWithProgress() {
