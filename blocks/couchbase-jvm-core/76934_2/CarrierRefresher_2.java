    private Observable<String> buildRefreshFallbackSequence(List<NodeInfo> nodeInfos, String bucketName) {
        Observable<String> failbackSequence = null;
        for (final NodeInfo nodeInfo : nodeInfos) {
            if (!isValidCarrierNode(environment.sslEnabled(), nodeInfo)) {
                continue;
            }

            if (failbackSequence == null) {
                failbackSequence = refreshAgainstNode(bucketName, nodeInfo.hostname());
            } else {
                failbackSequence = failbackSequence.onErrorResumeNext(
                        refreshAgainstNode(bucketName, nodeInfo.hostname())
                );
            }
        }
        if (failbackSequence == null) {
            LOGGER.debug("Could not build refresh sequence, node list is empty - ignoring attempt.");
            return Observable.empty();
        }
        return failbackSequence;
    }

     <T> void shiftNodeList(List<T> nodeList) {
         int shiftBy = (int) (nodeOffset++ % nodeList.size());
         for(int i = 0; i < shiftBy; i++) {
             T element = nodeList.remove(0);
             nodeList.add(element);
         }
     }

