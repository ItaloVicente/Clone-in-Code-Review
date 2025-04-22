        boolean success = false;
        int retries = 0;
        do {
          if(retries >= MAX_ADDOP_RETRIES) {
            op.cancel();
            break;
          }
          ViewNode node = couchNodes.get(getNextNode());
          if(node.isShuttingDown()) {
            continue;
          }
          if(retries > 0) {
            System.out.println("Retrying operation on node: " + node.getSocketAddress());
          }
          success = node.writeOp(op);
          retries++;
        } while(!success);
