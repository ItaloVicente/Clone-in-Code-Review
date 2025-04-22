				} catch (CoreException e) {
					handleError(UIText.Activator_refreshFailed, e, false);
					return new Status(IStatus.ERROR, getPluginId(), e.getMessage());
				} finally {
					getJobManager().endRule(rule);
				}
