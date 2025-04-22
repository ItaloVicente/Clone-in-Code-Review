		@Override
		protected void run(IProgressMonitor monitor)
				throws InterruptedException, InvocationTargetException {
			listOp.run(monitor);
			List<Change> changes = new ArrayList<>();
			for (Ref ref : listOp.getRemoteRefs()) {
				Change change = Change.fromRef(ref.getName());
				if (change != null) {
					changes.add(change);
