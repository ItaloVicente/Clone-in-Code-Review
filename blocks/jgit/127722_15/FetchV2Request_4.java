		Builder setAgent(@Nullable String agentLine) {
			this.agent = agentLine;
			return this;
		}

		Builder addServerOption(@NonNull String line) {
			this.serverOptions.add(line);
			return this;
		}

