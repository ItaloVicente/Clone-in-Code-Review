				for (int i = 0; i < repositories.length; i++) {
					try {
						Ref ref = repositories[i].getRefDatabase()
								.getRefs(Constants.R_HEADS).get(shortName);
						if (ref != null) {
							String fullName = ref.getName();
							BranchOperationUI
									.checkout(repositories[i], fullName)
									.start();
						}
					} catch (IOException ex) {
						Activator.handleError(ex.getMessage(), ex, true);
					}
				}
