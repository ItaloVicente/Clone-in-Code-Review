			@Override
			public EClassifier getType(EFactory eFactory, String typeName) {
				if (deprecatedTypeMappings.containsKey(typeName)) {
					final EClass tempEClass = EcoreFactory.eINSTANCE.createEClass();
					tempEClass.setName(typeName);
					return tempEClass;
				}
				return super.getType(eFactory, typeName);
			}

