                            try {
                                decorator = (ILabelDecorator) WorkbenchPlugin
                                        .createExtension(
                                                definingElement,
                                                DecoratorDefinition.ATT_CLASS);
                                decorator.addListener(WorkbenchPlugin
                                        .getDefault().getDecoratorManager());
                            } catch (CoreException exception) {
                                exceptions[0] = exception;
                            }
                        }
                    });
        } else {
