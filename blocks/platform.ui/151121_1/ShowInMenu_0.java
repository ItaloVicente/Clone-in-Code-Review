					List<MParameter> menuParameters = Optional.of(((MHandledMenuItem) menuElement).getParameters())
							.orElse(Collections.emptyList());
					ccip.parameters = new HashMap<>(menuParameters.size());

					for (MParameter menuParam : menuParameters) {
						ccip.parameters.put(menuParam.getName(), menuParam.getValue());
					}
