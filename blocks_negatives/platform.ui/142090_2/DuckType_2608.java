   /**
    * Interface DuckType#Wrapper.  An interface for DuckType proxies that
    * allows clients to access the proxied value.  The value returned by
    * calling DuckType#implement always implements this interface.
    */
   public static interface Wrapper {
      /**
       * Method duckType_GetWrappedValue.  Returns the proxied value.
       *
       * @return The proxied value.
       */
      public Object duckType_GetWrappedValue();
   }
