            this.replicated = replicated;
            this.persisted = persisted;
            this.persistedMaster = persistedMaster;
        }


        private ObserveItem(int replicated, int persisted, boolean persistedMaster) {
            this.replicated = replicated;
            this.persisted = persisted;
            this.persistedMaster = persistedMaster;
        }

        /**
         * An empty {@link Observe.ObserveItem}, when nothing has been observed yet.
         */
        public ObserveItem() {
            this(0, 0, false);
        }

        /**
         * Aggregate two ObserveItems together, merging the state they represent.
         *
         * @param other the other ObserveItem to aggregate with.
         * @return a new {@link ObserveItem} representing the aggregated state of this and the other state.
         */
        public ObserveItem add(ObserveItem other) {
            return new ObserveItem(this.replicated + other.replicated,
                    this.persisted + other.persisted,
                    this.persistedMaster || other.persistedMaster);
        }

        /**
         * Checks the state to see if it matches the given criteria.
         *
         * @param persistTo minimum number of persistence to be observed for this to match.
         * @param replicateTo minimum number of replications to be observed for this to match.
         * @return true if the current state matches the given criteria.
         */
        public boolean check(PersistTo persistTo, ReplicateTo replicateTo) {
            boolean persistDone = false;
            boolean replicateDone = false;

            if (persistTo == PersistTo.MASTER) {
                if (persistedMaster) {
                    persistDone = true;
                }
            } else if (persisted >= persistTo.value()) {
                persistDone = true;
            }

            if (replicated >= replicateTo.value()) {
                replicateDone = true;
            }

            return persistDone && replicateDone;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("persisted ").append(persisted);
            if (persistedMaster)
                sb.append(" (master)");
            sb.append(", replicated ").append(replicated);
            return sb.toString();
        }
    }
