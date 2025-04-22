                                    if (replicas >= 1) {
                                        obs.add(core.<ObserveResponse>send(new ObserveRequest(id, cas, false, (short) 1, bucket)));
                                    }
                                    if (replicas >= 2) {
                                        obs.add(core.<ObserveResponse>send(new ObserveRequest(id, cas, false, (short) 2, bucket)));
                                    }
                                    if (replicas == 3) {
                                        obs.add(core.<ObserveResponse>send(new ObserveRequest(id, cas, false, (short) 3, bucket)));
