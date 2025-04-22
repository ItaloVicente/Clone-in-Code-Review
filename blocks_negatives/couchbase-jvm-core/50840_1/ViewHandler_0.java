    /**
     * A utility method to split a GET-like query String into a {@link Tuple2} of Strings if size
     * of the original gets above a threshold. If not, the original String is returned as value1
     * of the tuple, and value2 is null. Otherwise, value1 is the original query string minus a "keys"
     * parameter, and value2 is a json representation of the keys parameter.
     *
     * @param queryString the GET-like query string to process if length is above threshold.
     * @param splitThreshold the size processing threshold.
     * @return a {@link Tuple2} with keys parameter isolated in value2 as a JSON object.
     */
    protected Tuple2<String, String> extractKeysFromQueryString(String queryString, int splitThreshold) {
        if (queryString.length() < splitThreshold) {
            return Tuple.create(queryString, null);
        }

        String[] params = queryString.split("&");
        StringBuilder reworkedQueryParams = new StringBuilder();
        StringBuilder keys = new StringBuilder(queryString.length());
        for (String param : params) {
            if (param.startsWith("keys=")) {
                keys.append("{\"keys\":").append(param.substring(5)).append('}');
            } else {
                reworkedQueryParams.append(param).append('&');
            }
        }
        if (reworkedQueryParams.length() > 0) {
            reworkedQueryParams.deleteCharAt(reworkedQueryParams.length() - 1);
        }

        return Tuple.create(reworkedQueryParams.toString(), keys.toString());
    }

