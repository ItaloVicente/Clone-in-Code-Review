      if (method.getName().equals("equals") && args != null && args.length == 1) {
         return Boolean.valueOf(equals(args[0]));
      }
      if (method.getName().equals("hashCode") && args == null) {
         return Integer.valueOf(hashCode());
      }
      if (method.getName().equals("duckType_GetWrappedValue") && args == null) {
         return object;
      }
