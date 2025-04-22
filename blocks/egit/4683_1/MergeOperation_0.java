				} catch (CheckoutConflictException e) {
					StringBuilder builder = new StringBuilder();
					for (String f : e.getConflictingPaths()) {
						builder.append("\n"); //$NON-NLS-1$
						builder.append(f);
					}
					throw new TeamException(
								new Status(
									IStatus.INFO,
									Activator.getPluginId(),
									MessageFormat.format(CoreText.MergeOperation_CheckoutConflict,
									builder.toString())));
