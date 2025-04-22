                focusLostHolder[0] = true;
            }
        });
        enterText(text, "hallo");
        assertTrue(focusLostHolder[0]);
    }

    @Test
