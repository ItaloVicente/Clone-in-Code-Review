							Object firstElement = selection.getFirstElement();
							if (firstElement instanceof StagingEntry)
								stage(selection);
							else {
								IResource resource = AdapterUtils.adapt(firstElement, IResource.class);
								if (resource != null)
									stage(selection);
							}
