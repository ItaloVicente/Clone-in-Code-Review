        @Override
        public Map<String, Object> customSettings() {
            return this.customSettings;
        }

        @Override
        public JsonObject raw() {
            return JsonObject.empty();
        }

        public Builder withSetting(String key, Object value) {
            this.customSettings.put(key, value);
            return this;
        }

        public Builder withSettings(Map<String, Object> customSettings) {
            this.customSettings.putAll(customSettings);
            return this;
        }

