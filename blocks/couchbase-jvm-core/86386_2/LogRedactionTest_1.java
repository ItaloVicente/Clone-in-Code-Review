    @Test
    public void shouldForwardMessageOnDebug() {
        Logger.getRootLogger().addAppender(appender);
        Log4JLogger logger = new Log4JLogger(Logger.getRootLogger(), RedactionLevel.FULL);

        logger.debug("Some {} stuff {} and {}", meta("meta"), user("user"), system(123));

        verify(appender).doAppend(logCaptor.capture());
        assertEquals("Some meta stuff user and 123", logCaptor.getValue().getRenderedMessage());
    }

