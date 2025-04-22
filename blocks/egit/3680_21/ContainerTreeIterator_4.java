			for (IResource resource : memberResources)
				if (!resource.isLinked()) {
					IPath location = resource.getLocation();
					if (location != null)
						resourceEntries.add(location.toFile());
				}
