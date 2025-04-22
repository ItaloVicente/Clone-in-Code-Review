            CouchbaseMockRunner couchbaseMock = new CouchbaseMockRunner();
            couchbaseMock.setDaemon(true);
            couchbaseMock.start();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                logger.error(ex);
            }
