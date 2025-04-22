    return core
        .<GetResponse>send(new GetRequest(id, bucket))
        .map(new Func1<GetResponse, D>() {
               @Override
               public D call(final GetResponse response) {
                 final Converter<?, Object> converter = (Converter<?, Object>) converters.get(target);
                 final Object content = (response.status() == ResponseStatus.SUCCESS) ? converter.decode(response.content(), response.flags()) : null;
                 return (D) converter.newDocument(id, content, response.cas(), 0, response.status());
               }
             }
        );
