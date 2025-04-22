		public Builder addServerOption(@Nullable String line) {
			if (line != null && !line.isEmpty()) {
				serverOptions.add(line);
			}

			return this;
		}

		public Builder setAgent(@Nullable String agentValue) {
			agent = agentValue;
			return this;
		}

