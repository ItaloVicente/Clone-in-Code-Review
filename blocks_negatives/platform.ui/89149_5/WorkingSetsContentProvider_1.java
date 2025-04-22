					Object[] res;
					if (extensionStateModel.getBooleanProperty(SHOW_OTHERS_WORKING_SET)) {
							res = new Object[activeWorkingSets.length + 1];
							System.arraycopy(activeWorkingSets, 0, res, 0, activeWorkingSets.length);
							res[activeWorkingSets.length] = OTHERS_WORKING_SET;
						} else {
							res = activeWorkingSets;
