				} else {
					Collection<String> filesToUpdate = visitor
							.getFilesToUpdate();
					Collection<IResource> resourcesToUpdate = visitor
							.getResourcesToUpdate();
					if (!filesToUpdate.isEmpty()) {
						scheduleUpdateJob(filesToUpdate, resourcesToUpdate);
					}
