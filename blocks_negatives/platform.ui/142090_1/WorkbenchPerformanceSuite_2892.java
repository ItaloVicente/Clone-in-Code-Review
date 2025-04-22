        IPerspectiveDescriptor[] perspectives = PlatformUI.getWorkbench().getPerspectiveRegistry().getPerspectives();

        for (int i = 0; i < perspectives.length; i++) {
            IPerspectiveDescriptor descriptor = perspectives[i];
            String id = descriptor.getId();
            result.add(id);
        }

        return result.toArray(new String[result.size()]);
    }

    /**
     *
     */
    private void addResizeScenarios() {
        String[] perspectiveIds = getAllPerspectiveIds();
        for (int i = 0; i < perspectiveIds.length; i++) {
            String id = perspectiveIds[i];
            addTest(new ResizeTest(new PerspectiveWidgetFactory(id),
                    id.equals(resizeFingerprintTest) ? BasicPerformanceTest.LOCAL : BasicPerformanceTest.NONE,
                            "UI - Workbench Window Resize"));
        }
    }
