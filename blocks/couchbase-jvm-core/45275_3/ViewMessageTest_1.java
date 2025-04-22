        single.info().toBlocking().forEach(new Action1<ByteBuf>() {
            @Override
            public void call(ByteBuf byteBuf) {
                ReferenceCountUtil.releaseLater(byteBuf);
            }
        });
        single.rows().toBlocking().forEach(new Action1<ByteBuf>() {
            @Override
            public void call(ByteBuf byteBuf) {
                ReferenceCountUtil.releaseLater(byteBuf);
            }
        });
