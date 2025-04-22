    /**
     * Method invoke.  If possible, invoke the encapsulated method with the
     * specified parameters.
     *
     * @param params An Object[] containing the parameters to pass.
     * @return any return value or null if there was no return value or an
     * error occured.
     */
    public Object invoke(Object[] params) {
        if (method == null)
            return null;
        try {
        	if (!method.isAccessible()) {
        		method.setAccessible(true);
        	}
        	return method.invoke(subject, params);
        } catch (Exception e) {
            return null;
        }
    }
