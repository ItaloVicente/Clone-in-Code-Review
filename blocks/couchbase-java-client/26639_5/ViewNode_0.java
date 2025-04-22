        boolean shouldRetry = shouldRetry(statusCode);
        getLogger().debug("Retrying View operation Request: "
          + op.getRequest().getRequestLine() + ", Response: "
          + response.getStatusLine());
        if(shouldRetry) {
          if(!op.isTimedOut() && !op.isCancelled()) {
            vconn.addOp(op);
          }
        } else {
          op.handleResponse(response);
        }
