			case MTestPackage.TEST_HARNESS: {
				MTestHarness testHarness = (MTestHarness)theEObject;
				T1 result = caseTestHarness(testHarness);
				if (result == null) result = caseCommand(testHarness);
				if (result == null) result = caseContext(testHarness);
				if (result == null) result = caseContribution(testHarness);
				if (result == null) result = caseElementContainer(testHarness);
				if (result == null) result = caseParameter(testHarness);
				if (result == null) result = caseUILabel(testHarness);
				if (result == null) result = caseDirtyable(testHarness);
				if (result == null) result = caseSnippetContainer(testHarness);
				if (result == null) result = caseUIElement(testHarness);
				if (result == null) result = caseApplicationElement(testHarness);
				if (result == null) result = caseLocalizable(testHarness);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
