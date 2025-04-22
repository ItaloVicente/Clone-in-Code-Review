				contents = contents.replaceAll("toSetter\\(Consumer<(\\w+)>", //$NON-NLS-1$
						"toSetter(Consumer<" + variant + "<$1>>"); //$NON-NLS-1$ //$NON-NLS-2$

				contents = contents.replaceAll("new SetterObservableValue<>\\(setter\\)", //$NON-NLS-1$
						"CommonStepsImpl.createSetter" + variant + "(setter)"); //$NON-NLS-1$ //$NON-NLS-2$

