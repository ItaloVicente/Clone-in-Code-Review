        /**
         * Add worked to the work.
         * @param workedIncrement
         */
        public void worked(double workedIncrement) {
            this.worked = this.worked + workedIncrement;
        }

        /**
         * Set the subTask name.
         * @param subTaskName
         */
        public void subTask(String subTaskName) {
            this.subTask = subTaskName;
        }

        /**
         * Run the collector.
         */
        @Override
