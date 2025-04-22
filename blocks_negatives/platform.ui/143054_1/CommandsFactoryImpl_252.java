			case CommandsPackageImpl.BINDING_CONTEXT: return (EObject)createBindingContext();
			case CommandsPackageImpl.BINDING_TABLE: return (EObject)createBindingTable();
			case CommandsPackageImpl.COMMAND: return (EObject)createCommand();
			case CommandsPackageImpl.COMMAND_PARAMETER: return (EObject)createCommandParameter();
			case CommandsPackageImpl.HANDLER: return (EObject)createHandler();
			case CommandsPackageImpl.KEY_BINDING: return (EObject)createKeyBinding();
			case CommandsPackageImpl.PARAMETER: return (EObject)createParameter();
			case CommandsPackageImpl.CATEGORY: return (EObject)createCategory();
			default:
