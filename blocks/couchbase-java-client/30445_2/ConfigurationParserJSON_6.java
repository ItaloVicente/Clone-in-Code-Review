
        Status status = null;
        if (currentNode.has("status")) {
          status = parseNodeStatus(currentNode.getString("status"));
        }

