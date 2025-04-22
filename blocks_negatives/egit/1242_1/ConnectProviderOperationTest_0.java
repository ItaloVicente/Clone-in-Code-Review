		final boolean f[] = new boolean[1];
		new Job("wait") {
			protected IStatus run(IProgressMonitor monitor) {

				f[0] = true;
				return null;
			}

			{
				setRule(project.getProject());
				schedule();
			}
		};
		while (!f[0]) {
			Thread.sleep(1000);
		}

