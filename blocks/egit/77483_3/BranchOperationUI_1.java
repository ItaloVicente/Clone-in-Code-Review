	private class CheckoutJob extends Job {

		private BranchOperation bop;

		private final boolean restore;

		public CheckoutJob(String jobName, boolean restore) {
			super(jobName);
			this.restore = restore;
		}

		@Override
		public IStatus run(IProgressMonitor monitor) {
			bop = new BranchOperation(repository, target, !restore);
			try {
				doCheckout(bop, restore, monitor);
			} catch (CoreException e) {
				switch (bop.getResult().getStatus()) {
				case CONFLICTS:
				case NONDELETED:
					break;
				default:
					return Activator.createErrorStatus(
							UIText.BranchAction_branchFailed, e);
				}
			} finally {
				GitLightweightDecorator.refresh();
				monitor.done();
			}
			return Status.OK_STATUS;
		}

		@Override
		public boolean belongsTo(Object family) {
			if (JobFamilies.CHECKOUT.equals(family))
				return true;
			return super.belongsTo(family);
		}

		@NonNull
		public CheckoutResult getCheckoutResult() {
			return bop.getResult();
		}
	}

