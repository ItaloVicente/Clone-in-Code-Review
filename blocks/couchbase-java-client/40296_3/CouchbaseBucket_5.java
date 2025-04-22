        return incoming
            .filter(new Func1<GetResponse, Boolean>() {
                @Override
                public Boolean call(GetResponse getResponse) {
                    return getResponse.status() == ResponseStatus.SUCCESS;
                }
            })
            .map(new Func1<GetResponse, D>() {
                @Override
                public D call(final GetResponse response) {
                    Converter<?, Object> converter = (Converter<?, Object>) converters.get(target);
                    Object content = response.status() == ResponseStatus.SUCCESS ? converter.decode(response.content()) : null;
                    return (D) converter.newDocument(id, content, response.cas(), 0, response.status());
                }
            });
