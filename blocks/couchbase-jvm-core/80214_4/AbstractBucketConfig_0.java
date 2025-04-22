                if (nodeInfos.size() == nodesExt.size()) {
                    hostname = nodeInfos.get(i).hostname();
                } else {
                    LOGGER.debug("Hostname is for nodesExt[{}] is not available, falling back to localhost.", i);
                    hostname = NetworkAddress.localhost();
                }
