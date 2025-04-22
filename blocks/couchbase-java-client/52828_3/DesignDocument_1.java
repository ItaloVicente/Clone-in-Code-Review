
        JsonObject opts = raw.getObject("options");
        final Map<Option, Long> options = new HashMap<Option, Long>();
        if (opts != null) {
            for (String key : opts.getNames()) {
                options.put(Option.fromName(key), opts.getLong(key));
            }
        }

        return new DesignDocument(name, views, options);
