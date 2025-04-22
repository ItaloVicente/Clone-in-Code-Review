            .isEmpty()
            .map(new Func1<Boolean, Boolean>() {
                @Override
                public Boolean call(Boolean notFound) {
                    return !notFound;
                }
            });
