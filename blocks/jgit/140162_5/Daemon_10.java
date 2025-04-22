		daemon.setReceivePackFactory((DaemonClient req
                    ReceivePack rp = factory.create(req
                    KetchLeader leader;
                    try {
                        leader = leaders.get(repo);
                    } catch (URISyntaxException err) {
                        throw new ServiceNotEnabledException(
                                KetchText.get().invalidFollowerUri
                    }
                    rp.setPreReceiveHook(new KetchPreReceive(leader));
                    return rp;
                });
