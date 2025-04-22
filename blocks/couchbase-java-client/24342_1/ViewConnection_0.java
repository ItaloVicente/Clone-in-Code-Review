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
          success = node.writeOp(op);
          retries++;
        } while(!success);
