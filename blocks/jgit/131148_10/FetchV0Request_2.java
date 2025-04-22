			for (String cap: clientCapabilities) {
				} else {
					clientCaps.add(cap);
				}
			}
			return this;
		}

		Builder setAgent(String clientAgent) {
			agent = clientAgent;
