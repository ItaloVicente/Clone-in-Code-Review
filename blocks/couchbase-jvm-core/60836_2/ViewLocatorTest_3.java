        locator.locate(request, nodes, configMock, null, null);
        verify(node1Mock, times(1)).send(request);
        verify(node2Mock, never()).send(request);
        verify(node3Mock, never()).send(request);

        locator.locate(request, nodes, configMock, null, null);
        verify(node1Mock, times(1)).send(request);
        verify(node2Mock, never()).send(request);
        verify(node3Mock, times(1)).send(request);

        locator.locate(request, nodes, configMock, null, null);
        verify(node1Mock, times(1)).send(request);
        verify(node2Mock, never()).send(request);
        verify(node3Mock, times(2)).send(request);

        locator.locate(request, nodes, configMock, null, null);
        verify(node1Mock, times(2)).send(request);
        verify(node2Mock, never()).send(request);
        verify(node3Mock, times(2)).send(request);
