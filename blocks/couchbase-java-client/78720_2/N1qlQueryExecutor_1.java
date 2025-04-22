                    try {
                        InetAddress hostname = InetAddress.getByName(nodeInfo.hostname().address());
                        GenericQueryRequest req = createN1qlRequest(query, bucket, username, password, hostname);
                        return core.send(req);
                    } catch (UnknownHostException e) {
                        return Observable.error(e);
                    }
