            Assert.assertEquals(result, actual.getReturnValue());
        } else {
            if (actual.getException() == null) {
                Assert.assertTrue("Test should have thrown exception " + expectedException + " but returned result "
                    + actual.getReturnValue(), false);
            }
            Assert.assertEquals("Test threw wrong type of exception", actual.getException().toString(), expectedException);
        }
    }
