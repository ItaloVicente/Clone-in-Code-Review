			case CommandsPackageImpl.BINDING_TABLE_CONTAINER: {
				MBindingTableContainer bindingTableContainer = (MBindingTableContainer)theEObject;
				T result = caseBindingTableContainer(bindingTableContainer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CommandsPackageImpl.BINDINGS: {
				MBindings bindings = (MBindings)theEObject;
				T result = caseBindings(bindings);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CommandsPackageImpl.BINDING_CONTEXT: {
				MBindingContext bindingContext = (MBindingContext)theEObject;
				T result = caseBindingContext(bindingContext);
				if (result == null) result = caseApplicationElement(bindingContext);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CommandsPackageImpl.BINDING_TABLE: {
				MBindingTable bindingTable = (MBindingTable)theEObject;
				T result = caseBindingTable(bindingTable);
				if (result == null) result = caseApplicationElement(bindingTable);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CommandsPackageImpl.COMMAND: {
				MCommand command = (MCommand)theEObject;
				T result = caseCommand(command);
				if (result == null) result = caseApplicationElement(command);
				if (result == null) result = caseLocalizable(command);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CommandsPackageImpl.COMMAND_PARAMETER: {
				MCommandParameter commandParameter = (MCommandParameter)theEObject;
				T result = caseCommandParameter(commandParameter);
				if (result == null) result = caseApplicationElement(commandParameter);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CommandsPackageImpl.HANDLER: {
				MHandler handler = (MHandler)theEObject;
				T result = caseHandler(handler);
				if (result == null) result = caseContribution(handler);
				if (result == null) result = caseApplicationElement(handler);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CommandsPackageImpl.HANDLER_CONTAINER: {
				MHandlerContainer handlerContainer = (MHandlerContainer)theEObject;
				T result = caseHandlerContainer(handlerContainer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CommandsPackageImpl.KEY_BINDING: {
				MKeyBinding keyBinding = (MKeyBinding)theEObject;
				T result = caseKeyBinding(keyBinding);
				if (result == null) result = caseApplicationElement(keyBinding);
				if (result == null) result = caseKeySequence(keyBinding);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CommandsPackageImpl.KEY_SEQUENCE: {
				MKeySequence keySequence = (MKeySequence)theEObject;
				T result = caseKeySequence(keySequence);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CommandsPackageImpl.PARAMETER: {
				MParameter parameter = (MParameter)theEObject;
				T result = caseParameter(parameter);
				if (result == null) result = caseApplicationElement(parameter);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CommandsPackageImpl.CATEGORY: {
				MCategory category = (MCategory)theEObject;
				T result = caseCategory(category);
				if (result == null) result = caseApplicationElement(category);
				if (result == null) result = caseLocalizable(category);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
