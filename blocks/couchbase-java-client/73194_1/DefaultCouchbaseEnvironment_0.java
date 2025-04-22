        @Override
        public Builder kvIoPool(EventLoopGroup group, ShutdownHook shutdownHook) {
            super.kvIoPool(group, shutdownHook);
            return this;
        }

        @Override
        public Builder viewIoPool(EventLoopGroup group, ShutdownHook shutdownHook) {
            super.viewIoPool(group, shutdownHook);
            return this;
        }

        @Override
        public Builder queryIoPool(EventLoopGroup group, ShutdownHook shutdownHook) {
            super.queryIoPool(group, shutdownHook);
            return this;
        }

        @Override
        public Builder searchIoPool(EventLoopGroup group, ShutdownHook shutdownHook) {
            super.searchIoPool(group, shutdownHook);
            return this;
        }

        @Override
        public Builder keyValueServiceConfig(KeyValueServiceConfig keyValueServiceConfig) {
            super.keyValueServiceConfig(keyValueServiceConfig);
            return this;
        }

        @Override
        public Builder viewServiceConfig(ViewServiceConfig viewServiceConfig) {
            super.viewServiceConfig(viewServiceConfig);
            return this;
        }

        @Override
        public Builder queryServiceConfig(QueryServiceConfig queryServiceConfig) {
            super.queryServiceConfig(queryServiceConfig);
            return this;
        }

        @Override
        public Builder searchServiceConfig(SearchServiceConfig searchServiceConfig) {
            super.searchServiceConfig(searchServiceConfig);
            return this;
        }

