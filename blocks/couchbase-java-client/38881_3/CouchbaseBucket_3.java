    return core
        .<GetResponse>send(new GetRequest(id, bucket))
        .map(new Func1<GetResponse, D>() {
               @Override
               public D call(final GetResponse response) {
                 final Converter<?, Object> converter = (Converter<?, Object>) converters.get(target);
                 return (D) converter.decode(
                     id,
                     (response.status() == ResponseStatus.SUCCESS) ? response.document() : null,
                     response.status()
                 );
               }
             }
        );
