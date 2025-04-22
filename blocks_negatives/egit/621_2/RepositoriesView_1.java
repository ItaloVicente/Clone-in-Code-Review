						@Override
						protected IStatus run(IProgressMonitor monitor) {

							Repository repo = node.getRepository();

							final BranchOperation op = new BranchOperation(
									repo, refName);
							IWorkspaceRunnable wsr = new IWorkspaceRunnable() {
