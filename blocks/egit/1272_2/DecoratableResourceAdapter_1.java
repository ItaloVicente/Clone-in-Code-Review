
			switch (resource.getType()) {
			case IResource.FILE:
				if (!treeWalk.next())
					return;
				extractResourceProperties(treeWalk);
				break;
			case IResource.PROJECT:
				tracked = true;
			case IResource.FOLDER:
				extractContainerProperties(treeWalk);
				break;
			}
		} finally {
			if (trace)
				GitTraceLocation
						.getTrace()
						.trace(GitTraceLocation.DECORATION.getLocation(),
								"Decoration took " + (System.currentTimeMillis() - start) //$NON-NLS-1$
										+ " ms"); //$NON-NLS-1$
