                new JsonPointer("/profile", new JsonPointerCB1() {
                    public void call(ByteBuf buf) {
                        if (queryProfileInfoObservable != null) {
                            queryProfileInfoObservable.onNext(buf);
                        }
                    }
                }),
