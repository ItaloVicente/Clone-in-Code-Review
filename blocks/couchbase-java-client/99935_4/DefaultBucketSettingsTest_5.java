    @Test
    public void shouldAllowToOverrideEjectionMethod() {
        for (EjectionMethod method : EjectionMethod.values()) {
            assertEquals(method, DefaultBucketSettings.builder().ejectionMethod(method).build().ejectionMethod());
        }
    }

