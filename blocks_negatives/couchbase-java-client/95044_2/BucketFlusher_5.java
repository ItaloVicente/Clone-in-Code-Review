                }
            })
            .take(1)
            .map(new Func1<Integer, Boolean>() {
                @Override
                public Boolean call(Integer integer) {
                    return true;
                }
            });
