
    @Test
    public void testVersionInformationDifferentFromCore() {
        CoreEnvironment coreEnv = DefaultCoreEnvironment.create();
        CouchbaseEnvironment env = DefaultCouchbaseEnvironment.create();

        assertNotEquals(coreEnv.packageNameAndVersion(), env.packageNameAndVersion());
        assertNotEquals(coreEnv.userAgent(), env.userAgent());
        assertTrue(env.toString(), env.toString().contains(env.packageNameAndVersion()));
        assertEquals(coreEnv.coreVersion(), env.coreVersion());
        assertEquals(coreEnv.coreBuild(), env.coreBuild());
        assertNotEquals(coreEnv.coreVersion(), env.clientVersion());
        assertNotEquals(coreEnv.coreBuild(), env.clientBuild());
    }

    @Test
    public void testForcedVersionInformationSameWithCore() {
        CoreEnvironment coreEnv = DefaultCoreEnvironment.builder()
                .packageNameAndVersion("foo")
                .userAgent("bar")
                .build();

        CouchbaseEnvironment env = DefaultCouchbaseEnvironment.builder()
                .packageNameAndVersion("foo")
                .userAgent("bar")
                .build();

        assertEquals(coreEnv.packageNameAndVersion(), env.packageNameAndVersion());
        assertEquals(coreEnv.userAgent(), env.userAgent());
        assertTrue(env.toString(), env.toString().contains(coreEnv.packageNameAndVersion()));
        assertEquals(coreEnv.coreVersion(), env.coreVersion());
        assertEquals(coreEnv.coreBuild(), env.coreBuild());
        assertNotEquals(coreEnv.coreVersion(), env.clientVersion());
        assertNotEquals(coreEnv.coreBuild(), env.clientBuild());
    }
