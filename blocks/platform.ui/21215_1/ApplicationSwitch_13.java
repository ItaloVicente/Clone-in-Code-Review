			case ApplicationPackageImpl.LIFECYCLE_AWARE: {
				MLifecycleAware lifecycleAware = (MLifecycleAware)theEObject;
				T1 result = caseLifecycleAware(lifecycleAware);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case ApplicationPackageImpl.LIFECYCLE_CONTRIBUTION: {
				MLifecycleContribution lifecycleContribution = (MLifecycleContribution)theEObject;
				T1 result = caseLifecycleContribution(lifecycleContribution);
				if (result == null) result = caseContribution(lifecycleContribution);
				if (result == null) result = caseApplicationElement(lifecycleContribution);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
