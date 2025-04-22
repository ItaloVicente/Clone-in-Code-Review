				if (messageProvider != null) {
					IResource[] resourcesArray = resources
							.toArray(new IResource[0]);
					calculatedCommitMessage = calculatedCommitMessage
							+ (messageProvider.getMessage(resourcesArray)
									.trim());
