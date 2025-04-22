        locator.locate(request, nodes, configMock, null, null);
        verify(node1Mock, never()).send(request);
        verify(node2Mock, times(1)).send(request);
        verify(node3Mock, never()).send(request);

        locator.locate(request, nodes, configMock, null, null);
        verify(node1Mock, never()).send(request);
        verify(node2Mock, times(2)).send(request);
        verify(node3Mock, never()).send(request);

        locator.locate(request, nodes, configMock, null, null);
        verify(node1Mock, never()).send(request);
        verify(node2Mock, times(3)).send(request);
        verify(node3Mock, never()).send(request);

        locator.locate(request, nodes, configMock, null, null);
        verify(node1Mock, never()).send(request);
        verify(node2Mock, times(4)).send(request);
        verify(node3Mock, never()).send(request);
