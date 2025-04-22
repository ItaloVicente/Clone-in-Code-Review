
        JsonObject opts = raw.getObject("options");
        final Map<Option, Object> options = new HashMap<Option, Object>();
        if (opts != null) {
            for (String key : opts.getNames()) {
                options.put(Option.fromName(key), opts.get(key));
            }
        }

        return new DesignDocument(name, views, options);
