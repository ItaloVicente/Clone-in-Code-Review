            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                logger.error(ex);
            }

            memcachedClient = new MemcachedClient(BASE_LIST, BUCKET_NAME, USER_NAME, USER_PASSWORD);
        } catch (Exception e) {
            logger.error(e);
            throw e;
