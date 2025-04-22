                        public void onNext(final ClusterConfig clusterConfig) {
                            int logicalNodes = nodes.size();
                            int configNodes = clusterConfig.allNodeAddresses().size();

                            if (logicalNodes != configNodes) {
                                LOGGER.debug("Number of logical Nodes does not match the number of nodes in the "
                                    + "current configuration! logical: {}, config: {}", logicalNodes, configNodes);
                            }
                        }
