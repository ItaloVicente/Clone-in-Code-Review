    private DefaultCoreEnvironment env;

    @After
    public void after(){
        if (env != null) {
            env.shutdown();
            env = null;
        }
    }

