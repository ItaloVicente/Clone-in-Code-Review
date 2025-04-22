    /**
     * This specialization of IRunnableContext#run(boolean, boolean,
     * IRunnableWithProgress) might run the runnable asynchronously
     * if <code>fork</code> is <code>true</code>.
     *
     * @since 3.2
     */
    @Override void run(boolean fork, boolean cancelable, IRunnableWithProgress runnable) throws InvocationTargetException, InterruptedException;
