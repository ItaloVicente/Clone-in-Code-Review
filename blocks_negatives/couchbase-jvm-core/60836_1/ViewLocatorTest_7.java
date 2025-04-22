        located = locator.locate(request, nodes, configMock);
        assertEquals(1, located.length);
        InetAddress foundLast = located[0].hostname();

        assertEquals(foundFirst, foundLast);
        assertNotEquals(foundFirst, foundSecond);
