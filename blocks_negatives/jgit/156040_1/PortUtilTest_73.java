    @Test
    public void testValidateOrGetNewWithPreferredNotAvailable() {
        int result1 = PortUtil.validateOrGetNew(0);

        try (ServerSocket ss = new ServerSocket(result1)) {
            int result = PortUtil.validateOrGetNew(result1);
            assertThat(result).isNotEqualTo(result1);
        } catch (Exception x) {
            fail("Port allocation should have work!");
        }
    }

    @Test
    public void testValidateOrGetNewWithZero() {
        int result1 = PortUtil.validateOrGetNew(0);

        assertThat(result1).isNotEqualTo(0);
    }

    @Test
    public void testValidateOrGetNewWithAvailablePreferredPort() {
        int result = PortUtil.validateOrGetNew(0);
        int result2 = PortUtil.validateOrGetNew(result);

        assertThat(result2).isEqualTo(result);
    }
