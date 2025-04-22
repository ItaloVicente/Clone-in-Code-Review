        /**
         * Put a default mapping into the editor map.
         *
         * @param key the key to set
         * @param value the value to associate
         */
        public void putDefault(String key, FileEditorMapping value) {
            defaultMap.put(key, value);
        }

        /**
         * Put a mapping into the user editor map.
         *
         * @param key the key to set
         * @param value the value to associate
         */
        public void put(String key, FileEditorMapping value) {
            Object result = defaultMap.get(key);
            if (value.equals(result)) {
