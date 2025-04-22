            CouchbaseEnvironment env = envBuilder.build();

            Cluster cluster = CouchbaseCluster.create(env, seedNode);
            return buildWithCluster(cluster, env);
        }

        public CouchbaseTestContext buildWithCluster(Cluster cluster, CouchbaseEnvironment env) {
