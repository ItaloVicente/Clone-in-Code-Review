			} else {
				for (IResource resource : resourcesSelected) {
					IPath location = resource.getLocation();
					if(location != null && location.toFile().equals(new File(uri))) {
						preselectionCandidates.add(fileName);
					}
