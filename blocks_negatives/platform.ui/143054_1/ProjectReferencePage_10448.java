                    }
                } catch (CoreException e) {
                }

                return referenced.toArray();
            }
        };
    }

    /**
     * Handle the exception thrown when saving.
     * @param e the exception
     */
    protected void handle(InvocationTargetException e) {
        IStatus error;
        Throwable target = e.getTargetException();
        if (target instanceof CoreException) {
            error = ((CoreException) target).getStatus();
        } else {
            String msg = target.getMessage();
            if (msg == null) {
