			ResourceTraversal traversal;
			IPath location = child.getLocation();

			if (child.isContainer()) {
				IContainer container = ROOT.getContainerForLocation(location);

				if (container == null)
					continue;
