            Version min = cluster.clusterManager(adminName, adminPassword).info().getMinVersion();
            boolean authed = false;
            if (min.major() >= 5) {
                cluster.authenticate(adminName, adminPassword);
                authed = true;
            }
            return buildWithCluster(cluster, env, authed);
