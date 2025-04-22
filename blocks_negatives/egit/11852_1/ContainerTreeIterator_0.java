			IPath containerLocation = node.getLocation();
			if (containerLocation != null) {
				File folder = containerLocation.toFile();
				File[] children = folder.listFiles();
				for (File child : children) {
					if (resourceEntries.contains(child))
					IPath childLocation = new Path(child.getAbsolutePath());
					IWorkspaceRoot root = node.getWorkspace().getRoot();
					IContainer container = root.getContainerForLocation(childLocation);
					if (container != null && container.isAccessible())
						entries.add(new ResourceEntry(container, false));
					else
						entries.add(new FileEntry(child, FS.DETECTED));
				}
			}
