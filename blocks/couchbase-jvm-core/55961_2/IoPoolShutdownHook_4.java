        try {
            ioPool.shutdownGracefully(0, 100, TimeUnit.MILLISECONDS);
            ThreadDeathWatcher.awaitInactivity(5, TimeUnit.SECONDS);
            return Observable.just(true);
        } catch (Throwable e) {
            return Observable.error(e);
        }
