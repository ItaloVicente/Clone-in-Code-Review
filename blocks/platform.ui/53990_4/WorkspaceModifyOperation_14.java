            IWorkspaceRunnable workspaceRunnable = pm -> {
			    try {
			        execute(pm);
			    } catch (InvocationTargetException e1) {
			        iteHolder[0] = e1;
			    } catch (InterruptedException e2) {
			        throw new OperationCanceledException(e2.getMessage());
			    }
			};
