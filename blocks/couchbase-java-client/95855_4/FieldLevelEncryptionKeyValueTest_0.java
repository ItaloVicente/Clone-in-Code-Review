        Assert.assertTrue((entity.stringa.contentEquals(stored.content().stringa)));
        Assert.assertTrue((entity.stringr.contentEquals(stored.content().stringr)));
        Assert.assertTrue(entity.boola == stored.content().boola);
        Assert.assertTrue(entity.boolr == stored.content().boolr);
        Assert.assertTrue(entity.ia == stored.content().ia);
        Assert.assertTrue(entity.ir == stored.content().ir);
