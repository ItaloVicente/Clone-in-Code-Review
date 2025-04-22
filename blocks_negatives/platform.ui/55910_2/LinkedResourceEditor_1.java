				try {
					monitor.beginTask(
							IDEWorkbenchMessages.LinkedResourceEditor_removingMessage,
							selectedResources.length);
					for (int i = 0; i < selectedResources.length; i++) {
						if (monitor.isCanceled())
							break;
						String fullPath = selectedResources[i]
								.getFullPath().toPortableString();
						try {
							selectedResources[i].delete(true, new SubProgressMonitor(monitor, 1));
							removedResources.add(selectedResources[i]);
							fBrokenResources.remove(fullPath);
							fFixedResources.remove(fullPath);
							fAbsoluteResources.remove(fullPath);
						} catch (CoreException e) {
							e.printStackTrace();
						}
