		Builder setAgent(String agentLine) {
			this.agent = agentLine;
			return this;
		}

		Builder addServerOption(String line) {
			this.serverOptions.add(line);
			return this;
		}

