            } else /* cas doesn't match */ {
              if (or == ObserveResponse.NOT_FOUND_PERSISTED) {
                response.put(node, or);
              } else {
                response.put(node, ObserveResponse.MODIFIED);
              }
