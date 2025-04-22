        public GetMap(CouchbaseEnvironment environment, Map<Class<? extends Document>,
          Transcoder<? extends Document, ?>> transcoders, Class<D> target, String id) {
          this.environment = environment;
          this.transcoders = transcoders;
          this.target = target;
          this.id = id;
