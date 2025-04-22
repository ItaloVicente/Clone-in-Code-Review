        boolean shouldRetry = shouldRetry(statusCode);
        if(shouldRetry) {
          if(!op.isTimedOut() && !op.isCancelled()) {
            getLogger().debug("Retrying View operation Request: "
              + op.getRequest().getRequestLine() + ", Response: "
              + response.getStatusLine());
            vconn.addOp(op);
          }
        } else {
          op.handleResponse(response);
        }
