    return core
        .<GetResponse>send(new GetRequest(id, bucket))
        .map(new Func1<GetResponse, D>() {
               @Override
               public D call(final GetResponse response) {
                 final Converter converter = converters.get(target);
                 return converter.decode(response.document());
               }
             }
        );
