                 @Override
                 public Observable<ReplaceResponse> call(UpsertResponse response) {
                     ReferenceCountUtil.releaseLater(response.content());
                     return cluster().send(new ReplaceRequest(key, Unpooled.copiedBuffer(content, CharsetUtil.UTF_8), bucket()));
                 }
            }).toBlocking().single();
