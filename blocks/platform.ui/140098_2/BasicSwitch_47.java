		case BasicPackageImpl.PART_DESCRIPTOR: {
			MPartDescriptor partDescriptor = (MPartDescriptor) theEObject;
			T result = casePartDescriptor(partDescriptor);
			if (result == null)
				result = caseApplicationElement(partDescriptor);
			if (result == null)
				result = caseUILabel(partDescriptor);
			if (result == null)
				result = caseHandlerContainer(partDescriptor);
			if (result == null)
				result = caseBindings(partDescriptor);
			if (result == null)
				result = caseLocalizable(partDescriptor);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case BasicPackageImpl.PART_DESCRIPTOR_CONTAINER: {
			MPartDescriptorContainer partDescriptorContainer = (MPartDescriptorContainer) theEObject;
			T result = casePartDescriptorContainer(partDescriptorContainer);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		default:
			return defaultCase(theEObject);
