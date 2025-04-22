					if (MergeResult.MergeStatus.NOT_SUPPORTED
							.equals(mergeResult.getMergeStatus())) {
						throw new TeamException(new Status(IStatus.INFO,
								Activator.getPluginId(),
								mergeResult.toString()));
					}
				} catch (IOException e) {
					throw new TeamException(
							CoreText.MergeOperation_InternalError, e);
