        HeavyNullImageDecorator.fail = true;
        HeavyNullTextDecorator.fail = true;
        NullImageDecorator.fail = true;
        DecoratorDefinition[] definitions = WorkbenchPlugin.getDefault()
                .getDecoratorManager().getAllDecoratorDefinitions();
        for (DecoratorDefinition definition2 : definitions) {
            String id = definition2.getId();
            if (id.equals("org.eclipse.ui.tests.heavyNullImageDecorator")
                    || id.equals("org.eclipse.ui.tests.heavyNullTextDecorator")) {
                definition2.setEnabled(true);
                problemDecorators.add(definition2);
            }
