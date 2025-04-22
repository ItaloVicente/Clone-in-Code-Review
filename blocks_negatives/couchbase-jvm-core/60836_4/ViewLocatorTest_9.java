        Node[] located = locator.locate(request, nodes, configMock);
        assertEquals(1, located.length);
        InetAddress foundFirst = located[0].hostname();

        located = locator.locate(request, nodes, configMock);
        assertEquals(1, located.length);
        InetAddress foundSecond = located[0].hostname();

        located = locator.locate(request, nodes, configMock);
        assertEquals(1, located.length);
        InetAddress foundThird = located[0].hostname();

        located = locator.locate(request, nodes, configMock);
        assertEquals(1, located.length);
        InetAddress foundFourth = located[0].hostname();

        assertEquals(foundFirst, InetAddress.getByName("192.168.56.101"));
        assertEquals(foundSecond, InetAddress.getByName("192.168.56.103"));
        assertEquals(foundThird, InetAddress.getByName("192.168.56.103"));
        assertEquals(foundFourth, InetAddress.getByName("192.168.56.101"));
