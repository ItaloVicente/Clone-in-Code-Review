		Job initJob = new Job("Initialize Git subscriber") { //$NON-NLS-1$
			{
				setSystem(true);
			}

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				GitResourceVariantTreeSubscriber gitSubscriber = new GitResourceVariantTreeSubscriber(
						set);
				gitSubscriber.init(new NullProgressMonitor());

				subscriber = gitSubscriber;
				return Status.OK_STATUS;
			}
