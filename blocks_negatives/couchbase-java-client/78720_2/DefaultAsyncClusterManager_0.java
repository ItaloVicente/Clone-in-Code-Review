                public InetAddress call(String hostname) {
                    try {
                        return InetAddress.getByName(hostname);
                    } catch(UnknownHostException e) {
                        throw new CouchbaseException(e);
                    }
