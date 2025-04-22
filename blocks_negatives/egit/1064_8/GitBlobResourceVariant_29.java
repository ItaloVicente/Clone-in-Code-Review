					try {
						return repository.open(id, Constants.OBJ_BLOB)
								.openStream();
					} catch (IOException err) {
						throw new TeamException(new Status(IStatus.ERROR,
								Activator.getPluginId(), err.getMessage(), err));
					}
