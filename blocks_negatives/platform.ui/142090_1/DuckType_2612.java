      if (obj instanceof Wrapper) {
         Wrapper proxy = (Wrapper) obj;
         Object wrappedValue = proxy.duckType_GetWrappedValue();
         return wrappedValue.equals(object);
      }
      return obj == this || super.equals(obj) || object.equals(obj);
   }
