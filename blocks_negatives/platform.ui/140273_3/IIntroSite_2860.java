    /**
     * Returns the key binding service in use.
     * <p>
     * The part will access this service to register
     * all of its actions, to set the active scope.
     * </p>
     *
     * @return the key binding service in use
     * @deprecated Use IServiceLocator#getService(*) to retrieve
     * IContextService and IHandlerService instead.
     */
    @Deprecated IKeyBindingService getKeyBindingService();
