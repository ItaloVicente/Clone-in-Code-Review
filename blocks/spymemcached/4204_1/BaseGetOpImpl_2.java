        @Override
        public void timedOut() {
            timedout = true;
        }

        @Override
        public boolean isTimedOut() {
            return timedout;
        }

