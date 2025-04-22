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

