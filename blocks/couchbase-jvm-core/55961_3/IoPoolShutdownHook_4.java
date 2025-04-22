        try {
            ioPool.shutdownGracefully(0, 10, TimeUnit.MILLISECONDS);
            ThreadDeathWatcher.awaitInactivity(1, TimeUnit.SECONDS);
            return Observable.just(true);
        } catch (Throwable e) {
            return Observable.error(e);
        }
