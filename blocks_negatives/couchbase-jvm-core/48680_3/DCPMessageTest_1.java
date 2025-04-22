        assertEquals(4, items.size());
        assertTrue(items.get(0) instanceof SnapshotMarkerMessage);
        MutationMessage mutation = (MutationMessage) items.get(1);
        assertEquals("foo", mutation.key());
        assertTrue(items.get(2) instanceof SnapshotMarkerMessage);
        RemoveMessage remove = (RemoveMessage) items.get(3);
        assertEquals("foo", remove.key());
