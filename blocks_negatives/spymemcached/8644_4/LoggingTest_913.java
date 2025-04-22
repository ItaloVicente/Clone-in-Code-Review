
    /**
     * Test when the last argument is not an exception.
     */
    public void testNoExceptionArg() throws Exception {
        Object[] args=new Object[]{"a", 42, new Exception("test"), "x"};
        Throwable t=((AbstractLogger)logger).getThrowable(args);
        assertNull(t);
    }

