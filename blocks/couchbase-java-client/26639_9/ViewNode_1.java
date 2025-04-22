        boolean shouldRetry = shouldRetry(statusCode, response);
        if(shouldRetry) {
          if(!op.isTimedOut() && !op.isCancelled()) {
            getLogger().info("Retrying HTTP operation Request: "
              + op.getRequest().getRequestLine() + ", Response: "
              + response.getStatusLine());
            vconn.addOp(op);
          }
        } else {
          op.handleResponse(response);
        }
