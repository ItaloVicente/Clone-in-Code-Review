        if(statusCode >= 300) {
          if(statusCode < 400) {
            getLogger().debug("Got Redirect, Retrying View Operation");
          } else if(statusCode < 500) {
            throw new RuntimeException("Got HTTP 400 (Client) Error: "
              + response.getStatusLine());
          } else {
            getLogger().warn("(" + op.getRequest().getRequestLine() + ") View "
              + "Response was Server Error (500) - Retrying OP: "
              + response.getStatusLine());
          }

          if(!op.isTimedOut() && !op.isCancelled()) {
            vconn.addOp(op);
          }
        } else {
          op.handleResponse(response);
        }
