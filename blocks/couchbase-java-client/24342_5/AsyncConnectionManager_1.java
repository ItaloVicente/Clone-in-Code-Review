          if(request != null) {
            NHttpClientConnection connection = request.getConnection();
            if(connection != null) {
              HttpOperation op = (HttpOperation)connection.getContext()
                .getAttribute("operation");
              requeueCallback.invoke(op);
            }
            request.cancel();
          }
