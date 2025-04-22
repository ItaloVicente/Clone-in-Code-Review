				if (environment != null) {
					for (Map.Entry<String, String> envVar : environment
							.entrySet()) {
						channel.setEnv(envVar.getKey(), envVar.getValue());
					}
				}
