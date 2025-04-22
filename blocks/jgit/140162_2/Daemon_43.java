		uploadPackFactory = (DaemonClient req
                    UploadPack up = new UploadPack(db);
                    up.setTimeout(getTimeout());
                    up.setPackConfig(getPackConfig());
                    return up;
                };

		receivePackFactory = (DaemonClient req
                    ReceivePack rp = new ReceivePack(db);
                    
                    InetAddress peer = req.getRemoteAddress();
                    String host = peer.getCanonicalHostName();
                    if (host == null)
                        host = peer.getHostAddress();
                    rp.setRefLogIdent(new PersonIdent(name
                    rp.setTimeout(getTimeout());
                    
                    return rp;
                };
