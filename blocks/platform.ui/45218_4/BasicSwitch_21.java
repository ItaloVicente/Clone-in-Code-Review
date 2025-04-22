			case BasicPackageImpl.FRAME: {
				MFrame frame = (MFrame)theEObject;
				T1 result = caseFrame(frame);
				if (result == null) result = caseElementContainer(frame);
				if (result == null) result = caseUILabel(frame);
				if (result == null) result = caseContext(frame);
				if (result == null) result = caseHandlerContainer(frame);
				if (result == null) result = caseBindings(frame);
				if (result == null) result = caseUIElement(frame);
				if (result == null) result = caseApplicationElement(frame);
				if (result == null) result = caseLocalizable(frame);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BasicPackageImpl.FRAME_ELEMENT: {
				MFrameElement frameElement = (MFrameElement)theEObject;
				T1 result = caseFrameElement(frameElement);
				if (result == null) result = caseUIElement(frameElement);
				if (result == null) result = caseApplicationElement(frameElement);
				if (result == null) result = caseLocalizable(frameElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
