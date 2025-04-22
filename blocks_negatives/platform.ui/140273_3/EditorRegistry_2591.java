        }

        /**
         * Return the mapping associated to the key. First searches user
         * map, and then falls back to the default map if there is no match. May
         * return <code>null</code>
         *
         * @param key
         *            the key to search for
         * @return the mapping associated to the key or <code>null</code>
         */
        public FileEditorMapping get(String key) {
            Object result = map.get(key);
            if (result == null) {
