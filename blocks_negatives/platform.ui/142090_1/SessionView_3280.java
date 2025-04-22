        Assert.assertNotNull(memento);

        Float bigFloat = memento.getFloat("float");
        Assert.assertNotNull(bigFloat);
        Assert.assertEquals(bigFloat.floatValue(), 0.50f, 0.0001);

        Integer bigInt = memento.getInteger("integer");
        Assert.assertEquals(bigInt, Integer.valueOf(50));

        String str = memento.getString("string");
        Assert.assertEquals(str, "50");

        IMemento child = memento.getChild("single");
        Assert.assertNotNull(child);
        bigInt = child.getInteger("id");
        Assert.assertEquals(bigInt, Integer.valueOf(1));

        bigInt = memento.getInteger("multiple.count");
        Assert.assertNotNull(bigInt);
        int count = bigInt.intValue();
        IMemento[] children = memento.getChildren("multiple");
        Assert.assertEquals(count, children.length);
        for (int nX = 0; nX < count; nX++) {
            child = children[nX];
            Assert.assertNotNull(child);
            bigInt = child.getInteger("id");
            Assert.assertEquals(bigInt, Integer.valueOf(nX));
        }
    }
