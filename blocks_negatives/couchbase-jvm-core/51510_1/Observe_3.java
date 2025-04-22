                    public Boolean call(List<ObserveResponse> observeResponses) {
                        int replicated = 0;
                        int persisted = 0;
                        boolean persistedMaster = false;
                        for (ObserveResponse response : observeResponses) {
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
                        }

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

                        return !(persistDone && replicateDone);
