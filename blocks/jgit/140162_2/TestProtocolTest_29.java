		TestProtocol<User> proto = registerProto((User req
                    if (!"user2".equals(req.name)) {
                        rejected.incrementAndGet();
                        throw new ServiceNotAuthorizedException();
                    }
                    return new UploadPack(db);
                }
