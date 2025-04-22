			for (IResource resource : resources) {
				try {
					resource.refreshLocal(IResource.DEPTH_INFINITE, null);
				} catch (CoreException e) {
					StatusManager.getManager().handle(e, IDEWorkbenchPlugin.IDE_WORKBENCH);
				}
			}
		}
	}

	@Override
