        public Builder configPollInterval(long configPollInterval) {
            if (configPollInterval < 2500 && configPollInterval != 0) {
                throw new IllegalArgumentException("The poll interval cannot be lower than " +
                    "2500 milliseconds");
            }
            this.configPollInterval = configPollInterval;
            return this;
        }

