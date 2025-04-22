        } else if (errorsRaw instanceof JsonObject) {
            JsonObject errorsJson = (JsonObject) errorsRaw;
            List<Exception> exceptions = new ArrayList<Exception>(errorsJson.size());
            for (String key : errorsJson.getNames()) {
                exceptions.add(new RuntimeException(key + ": " + errorsJson.get(key)));
            }
            errors = Observable.error(new CompositeException(exceptions));
