                                      }
                                  }
                              });
                }
            });
    }

    private static Expression expressionOrIdentifier(Object o) {
        if (o instanceof Expression) {
            return (Expression) o;
        } else if (o instanceof String) {
            return i((String) o);
        } else {
            throw new IllegalArgumentException("Fields for index must be either an Expression or a String identifier");
        }
