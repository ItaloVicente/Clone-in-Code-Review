		TestProtocol<User> proto = registerProto(new DefaultUpload()
                    if (!"user2".equals(req.name)) {
                        rejected.incrementAndGet();
                        throw new ServiceNotAuthorizedException();
                    }
                    return new ReceivePack(db);
                });
