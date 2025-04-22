		Builder setAgent(@Nullable String agentValue) {
			agent = agentValue;
			return this;
		}

		Builder addServerOption(@NonNull String value) {
			serverOptions.add(value);
			return this;
		}

