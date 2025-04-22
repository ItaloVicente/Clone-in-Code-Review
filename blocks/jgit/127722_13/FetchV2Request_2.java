		Builder setAgent(@Nullable String agentLine) {
			this.agent = agentLine;
			return this;
		}

		Builder addServerOption(@Nullable String line) {
			if (line != null && !line.isEmpty()) {
				this.serverOptions.add(line);
			}

			return this;
		}

