        Tree tree = (Tree) widgets.get(0);
        assertEquals(workingSetItems.length, tree.getItemCount());
        assertTrue(ArrayUtil.contains(workingSetItems, p1));
        assertTrue(ArrayUtil.contains(workingSetItems, p2));

        /*
         * Check page texts
         */
