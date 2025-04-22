            IWorkspaceRunnable workspaceRunnable = new IWorkspaceRunnable() {
                @Override
				public void run(IProgressMonitor pm) throws CoreException {
                    try {
                        execute(pm);
                    } catch (InvocationTargetException e) {
                        iteHolder[0] = e;
                    } catch (InterruptedException e) {
                        throw new OperationCanceledException(e.getMessage());
                    }
                }
            };
