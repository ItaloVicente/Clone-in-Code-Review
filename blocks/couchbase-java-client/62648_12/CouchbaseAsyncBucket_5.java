                Observable<SearchQueryRow> errors;
                JsonArray errorsJson = jsonStatus.getArray("errors");
                if (errorsJson != null) {
                    List<Exception> exceptions = new ArrayList<Exception>(errorsJson.size());
                    for (Object o : errorsJson) {
                        exceptions.add(new RuntimeException(String.valueOf(o)));
                    }
                    errors = Observable.error(new CompositeException(exceptions));
                } else {
                    errors = Observable.empty();
