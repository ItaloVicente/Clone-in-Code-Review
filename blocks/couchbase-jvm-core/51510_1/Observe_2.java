
    private static class ObserveItem {

        private final int replicated;
        private final int persisted;
        private final boolean persistedMaster;


        public ObserveItem(String id, ObserveResponse response, long cas, boolean remove,
                           ObserveResponse.ObserveStatus persistIdentifier,
                           ObserveResponse.ObserveStatus replicaIdentifier) {
            int replicated = 0;
            int persisted = 0;
            boolean persistedMaster = false;


            if (response.content() != null && response.content().refCnt() > 0) {
                response.content().release();
            }
            ObserveResponse.ObserveStatus status = response.observeStatus();

            boolean validCas = cas == response.cas()
                    || (remove && response.cas() == 0 && status == persistIdentifier);

            if (response.master()) {
                if (!validCas) {
                    throw new DocumentConcurrentlyModifiedException("The CAS on the active node "
                            + "changed for ID \"" + id + "\", indicating it has been modified in the "
                            + "meantime.");
                }

                if (status == persistIdentifier) {
                    persisted++;
                    persistedMaster = true;
                }
            } else if (validCas) {
                if (status == persistIdentifier) {
                    persisted++;
                    replicated++;
                } else if (status == replicaIdentifier) {
                    replicated++;
                }
            }

            this.replicated = replicated;
            this.persisted = persisted;
            this.persistedMaster = persistedMaster;
        }


        private ObserveItem(int replicated, int persisted, boolean persistedMaster) {
            this.replicated = replicated;
            this.persisted = persisted;
            this.persistedMaster = persistedMaster;
        }

        public ObserveItem() {
            this(0, 0, false);
        }

        public ObserveItem add(ObserveItem other) {
            return new ObserveItem(this.replicated + other.replicated,
                    this.persisted + other.persisted,
                    this.persistedMaster || other.persistedMaster);
        }

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
