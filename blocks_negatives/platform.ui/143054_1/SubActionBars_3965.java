					final Map activationsByActionId = (Map) activationsByActionIdByServiceLocator
							.get(serviceLocator);
					if ((activationsByActionId != null)
							&& (activationsByActionId.containsKey(actionID))) {
						final Object value = activationsByActionId
								.remove(actionID);
