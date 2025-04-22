    for (Object o : extraHeaders) {
      if (o instanceof Integer) {
        extraLen += 4;
      } else if (o instanceof byte[]) {
        extraLen += ((byte[]) o).length;
      } else if (o instanceof Long) {
        extraLen += 8;
      } else  if (o instanceof Short) {
        extraLen += 2;
      } else {
        assert false : "Unhandled extra header type:  " + o.getClass();
      }
