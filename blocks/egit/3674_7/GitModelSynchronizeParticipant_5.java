				} else if (property.equals(P_NAVIGATOR)) {
					Object oldNavigator = configuration
							.getProperty(P_NAVIGATOR);
					if (!(oldNavigator instanceof GitTreeCompareNavigator))
						configuration.setProperty(P_NAVIGATOR,
								new GitTreeCompareNavigator(
										(CompareNavigator) oldNavigator));
