                super.partHidden(ref);
                assertEquals(MockViewPart.ID, ref.getId());
                assertNull(fPage.findView(MockViewPart.ID));
                eventReceived[0] = true;
                assertFalse(eventReceived[1]);
            }
            @Override
