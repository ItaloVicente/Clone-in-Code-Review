            InetAddress lookupResult;
            try {
                lookupResult = InetAddress.getByName(alternate != null ? alternate : hostname);
            } catch (UnknownHostException e) {
                throw new CouchbaseException(e); // TODO: I think this needs to go the alternate if set
            }
