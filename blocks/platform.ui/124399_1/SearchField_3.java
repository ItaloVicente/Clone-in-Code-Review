				txtQuickAccess.setEnabled(false);
				String originalTooltip = txtQuickAccess.getToolTipText();
				txtQuickAccess.setToolTipText("restoring quick access elements"); //$NON-NLS-1$
				UIJob enableQuickAccess = new UIJob("finish restoring quick access elements") { //$NON-NLS-1$

					@Override
					public IStatus runInUIThread(IProgressMonitor monitor) {
						txtQuickAccess.setEnabled(true);
						txtQuickAccess.setToolTipText(originalTooltip);
						return Status.OK_STATUS;
					}

				};
				enableQuickAccess.setSystem(true);
				restoreDialogEntriesJob = new Job("restore quick access elements") { //$NON-NLS-1$
					@Override
					protected IStatus run(IProgressMonitor jobMonitor) {
						try {
							int arrayIndex = 0;

							SubMonitor monitor = SubMonitor.convert(jobMonitor, "restoring quick access elements", //$NON-NLS-1$
									orderedElements.length);

							for (int i = 0; i < orderedElements.length; i++) {
								try {
									System.out.println("loading " + orderedElements[i]); //$NON-NLS-1$
									Thread.sleep(2_000);
								} catch (InterruptedException e) {
