						return Status.OK_STATUS;
					}

				};
				showProposals.schedule();
			};
			if (container instanceof NonBlockingWizardDialog) {
				NonBlockingWizardDialog dialog = (NonBlockingWizardDialog) container;
				dialog.run(operation,
						() -> list.cancel(ChangeList.CancelMode.ABANDON));
			} else {
				container.run(true, true, operation);
			}
			return null;
