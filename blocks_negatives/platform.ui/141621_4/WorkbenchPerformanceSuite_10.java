        for (int i = 0; i < perspectiveIds.length; i++) {
            String id = perspectiveIds[i];
            addTest(new ResizeTest(new PerspectiveWidgetFactory(id),
                    id.equals(resizeFingerprintTest) ? BasicPerformanceTest.LOCAL : BasicPerformanceTest.NONE,
                            "UI - Workbench Window Resize"));
        }
