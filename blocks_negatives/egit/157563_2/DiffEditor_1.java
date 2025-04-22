				new UIJob(UIText.DiffEditor_TaskUpdatingViewer) {

					@Override
					public IStatus runInUIThread(IProgressMonitor uiMonitor) {
						if (UIUtils
								.isUsable(getSourceViewer().getTextWidget())) {
							input.setDocument(document);
							setInput(input);
						}
						return Status.OK_STATUS;
					}
				}.schedule();
				return Status.OK_STATUS;
