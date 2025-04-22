    public Properties() {
    }

    public Properties(final Map<String, Object> original) {
        for (Entry<String, Object> e : original.entrySet()) {
            if (e.getValue() != null) {
                put(e.getKey(),
                    e.getValue());
            }
        }
    }

    public Object put(final String key,
                      final Object value) {
        if (value == null) {
            return remove(key);
        }
        return super.put(key,
                         value);
    }

    public void store(final OutputStream out) {
        store(out,
              true);
    }

    public void store(final OutputStream out,
                      boolean closeOnFinish) {
    }

    public void load(final InputStream in) {
        load(in,
             true);
    }

    public void load(final InputStream in,
                     boolean closeOnFinish) {
    }
