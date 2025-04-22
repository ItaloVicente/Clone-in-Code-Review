        return Observable.from(ioPool.shutdownGracefully()).map(new Func1<Object, Boolean>() {
            @Override
            public Boolean call(Object o) {
                return true;
            }
        });
