          if(request != null) {
            NHttpClientConnection connection = request.getConnection();
            if(connection != null) {
              HttpOperation op = (HttpOperation)connection.getContext()
                .getAttribute("operation");
              System.out.println("*** Requeuing Op: " + op);
              requeueCallback.invoke(op);
            }
            request.cancel();
          }
