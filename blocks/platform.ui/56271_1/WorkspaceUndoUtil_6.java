					IResource[] children = ((IContainer) source).members();
					if (source.isLinked() && source.getLocation().equals(existing.getLocation()))
						children = filterNonLinkedResources(children);
					ResourceDescription[] overwritten = move(children, destinationPath, resourcesAtDestination,
							reverseDestinations, iterationProgress.newChild(90), uiInfo, false);
					for (int j = 0; j < overwritten.length; j++) {
						overwrittenResources.add(overwritten[j]);
					}
