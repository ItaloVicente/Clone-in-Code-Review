        @Override
        public String userAgent() {
            if (userAgent == null) {
                return String.format("%s (%s/%s %s; %s %s)", packageNameAndVersion,
                        System.getProperty("os.name"), System.getProperty("os.version"), System.getProperty("os.arch"),
                        System.getProperty("java.vm.name"), System.getProperty("java.runtime.version"));
            } else {
                return userAgent;
            }
        }

        public void userAgent(String userAgent) {
            this.userAgent = userAgent;
        }

        @Override
        public String packageNameAndVersion() {
            return packageNameAndVersion;
        }

        public void packageNameAndVersion(String packageNameAndVersion) {
            this.packageNameAndVersion = packageNameAndVersion;
        }

        private String defaultPackageNameAndVersion() {
            String version = Package.getPackage("com.couchbase.client.core").getSpecificationVersion();
            String gitVersion = Package.getPackage("com.couchbase.client.core").getImplementationVersion();
            return String.format("couchbase-jvm-core/%s (git: %s)",
                    version == null ? "unknown" : version, gitVersion == null ? "unknown" : gitVersion);
        }

