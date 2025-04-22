					List<MParameter> menuParameters = ((MHandledMenuItem) menuElement).getParameters();
					if (menuParameters != null && !menuParameters.isEmpty()) {
						ccip.parameters = new HashMap<>(menuParameters.size());

						for (MParameter menuParam : menuParameters) {
							ccip.parameters.put(menuParam.getName(), menuParam.getValue());
						}
					}
