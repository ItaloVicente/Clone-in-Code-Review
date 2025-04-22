			} else {
				for (IResource resource : selectedResources) {
					if(resource.getFullPath().toFile().equals(new File(uri))) {
						preselectionCandidates.add(fileName);
					}
				}
