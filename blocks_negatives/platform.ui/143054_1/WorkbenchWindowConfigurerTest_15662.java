                    IActionBarConfigurer actionBarConfig, int flags) {
                super.fillActionBars(window, actionBarConfig, flags);


                IWorkbenchWindowConfigurer windowConfig = workbenchConfig
                        .getWindowConfigurer(window);
                assertNotNull(windowConfig);

                assertEquals(window, windowConfig.getWindow());
                assertEquals(workbenchConfig, windowConfig
                        .getWorkbenchConfigurer());
                assertEquals(actionBarConfig, windowConfig
                        .getActionBarConfigurer());
                assertNotNull(windowConfig.getTitle());
                assertTrue(windowConfig.getShowCoolBar());
                assertTrue(windowConfig.getShowMenuBar());
                assertFalse(windowConfig.getShowPerspectiveBar());
                assertTrue(windowConfig.getShowStatusLine());

                windowConfig.setTitle(tempTitle);
                windowConfig.setShowCoolBar(false);
                windowConfig.setShowMenuBar(false);
                windowConfig.setShowPerspectiveBar(true);
                windowConfig.setShowStatusLine(false);
                assertEquals(tempTitle, windowConfig.getTitle());
                assertFalse(windowConfig.getShowCoolBar());
                assertFalse(windowConfig.getShowMenuBar());
                assertTrue(windowConfig.getShowPerspectiveBar());
                assertFalse(windowConfig.getShowStatusLine());

                windowConfig.setShowCoolBar(true);
                windowConfig.setShowMenuBar(true);
                windowConfig.setShowPerspectiveBar(false);
                windowConfig.setShowStatusLine(true);
            }
        };

        int code = PlatformUI.createAndRunWorkbench(display, wa);
        assertEquals(PlatformUI.RETURN_OK, code);
    }
