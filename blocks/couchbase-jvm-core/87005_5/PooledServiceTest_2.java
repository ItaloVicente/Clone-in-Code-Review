        assertTryUntil(new Func0<Boolean>() {
            @Override
            public Boolean call() {
                return ms.endpoints().size() == 1;
            }
        });
