                        .delay(100, TimeUnit.MILLISECONDS)
                        .filter(new Func1<ClusterInfo, Boolean>() {
                            @Override
                            public Boolean call(ClusterInfo clusterInfo) {
                                boolean allHealthy = true;
                                for (Object n : clusterInfo.raw().getArray("nodes")) {
                                    JsonObject node = (JsonObject) n;
                                    if (!node.getString("status").equals("healthy")) {
                                        allHealthy = false;
                                        break;
                                    }
