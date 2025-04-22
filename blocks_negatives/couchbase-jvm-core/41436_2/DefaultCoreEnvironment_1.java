        }

        private String defaultPackageNameAndVersion() {
            String version = Package.getPackage("com.couchbase.client.core").getSpecificationVersion();
            String gitVersion = Package.getPackage("com.couchbase.client.core").getImplementationVersion();
            return String.format("couchbase-jvm-core/%s (git: %s)",
                    version == null ? "unknown" : version, gitVersion == null ? "unknown" : gitVersion);
        }

        public Builder queryEndpoints(final int queryServiceEndpoints) {
            this.queryServiceEndpoints = queryServiceEndpoints;
