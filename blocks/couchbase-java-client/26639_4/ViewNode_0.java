        if(statusCode >= 300) {
          if(statusCode < 400) {
            getLogger().debug("Got Redirect, Retrying View Operation");
          } else {
            getLogger().warn("(Req: " + op.getRequest().getRequestLine()
              + ") View "
              + "Response HTTP Code (" + statusCode + ") - Retrying OP: "
              + response.getStatusLine());
          }

          if(!op.isTimedOut() && !op.isCancelled()) {
            vconn.addOp(op);
          }
        } else {
          op.handleResponse(response);
        }
