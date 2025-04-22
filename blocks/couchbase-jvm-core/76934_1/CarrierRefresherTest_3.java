
    @Test
    public void shouldShiftNodeList() {
        ClusterFacade cluster = mock(ClusterFacade.class);
        CarrierRefresher refresher = new CarrierRefresher(ENVIRONMENT, cluster);

        List<Integer> list = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3, 4));
        refresher.shiftNodeList(list);
        assertEquals(Arrays.asList(0, 1, 2, 3, 4), list); // shift by 0

        list = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3, 4));
        refresher.shiftNodeList(list);
        assertEquals(Arrays.asList(1, 2, 3, 4, 0), list); // shift by 1

        list = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3, 4));
        refresher.shiftNodeList(list);
        assertEquals(Arrays.asList(2, 3, 4, 0, 1), list); // shift by 2

        list = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3, 4));
        refresher.shiftNodeList(list);
        assertEquals(Arrays.asList(3, 4, 0, 1, 2), list); // shift by 3

        list = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3, 4));
        refresher.shiftNodeList(list);
        assertEquals(Arrays.asList(4, 0, 1, 2, 3), list); // shift by 4

        list = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3, 4));
        refresher.shiftNodeList(list);
        assertEquals(Arrays.asList(0, 1, 2, 3, 4), list); // shift by 0

        list = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3, 4));
        refresher.shiftNodeList(list);
        assertEquals(Arrays.asList(1, 2, 3, 4, 0), list); // shift by 1
    }

    @Test
    public void shouldUseShiftedNodeListOnTaintedPolling() {

    }

    @Test
    public void shouldUseShiftedNodeListOnRefreshPolling() {

    }
