        List<ViewNode> newNodes = createConnections(newServers);

        List<ViewNode> mergedNodes = new ArrayList<ViewNode>();
        mergedNodes.addAll(stayNodes);
        mergedNodes.addAll(newNodes);

        couchNodes = mergedNodes;
      } finally {
        wlock.unlock();
