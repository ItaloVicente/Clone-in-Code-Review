			case ApplicationPackageImpl.APPLICATION__PROPERTIES:
				return ((InternalEList<?>)((EMap.InternalMapView<String, String>)getProperties()).eMap()).basicRemove(otherEnd, msgs);
			case ApplicationPackageImpl.APPLICATION__HANDLERS:
				return ((InternalEList<?>)getHandlers()).basicRemove(otherEnd, msgs);
			case ApplicationPackageImpl.APPLICATION__BINDING_TABLES:
				return ((InternalEList<?>)getBindingTables()).basicRemove(otherEnd, msgs);
			case ApplicationPackageImpl.APPLICATION__ROOT_CONTEXT:
				return ((InternalEList<?>)getRootContext()).basicRemove(otherEnd, msgs);
			case ApplicationPackageImpl.APPLICATION__DESCRIPTORS:
				return ((InternalEList<?>)getDescriptors()).basicRemove(otherEnd, msgs);
			case ApplicationPackageImpl.APPLICATION__MENU_CONTRIBUTIONS:
				return ((InternalEList<?>)getMenuContributions()).basicRemove(otherEnd, msgs);
			case ApplicationPackageImpl.APPLICATION__TOOL_BAR_CONTRIBUTIONS:
				return ((InternalEList<?>)getToolBarContributions()).basicRemove(otherEnd, msgs);
			case ApplicationPackageImpl.APPLICATION__TRIM_CONTRIBUTIONS:
				return ((InternalEList<?>)getTrimContributions()).basicRemove(otherEnd, msgs);
			case ApplicationPackageImpl.APPLICATION__SNIPPETS:
				return ((InternalEList<?>)getSnippets()).basicRemove(otherEnd, msgs);
			case ApplicationPackageImpl.APPLICATION__COMMANDS:
				return ((InternalEList<?>)getCommands()).basicRemove(otherEnd, msgs);
			case ApplicationPackageImpl.APPLICATION__ADDONS:
				return ((InternalEList<?>)getAddons()).basicRemove(otherEnd, msgs);
			case ApplicationPackageImpl.APPLICATION__CATEGORIES:
				return ((InternalEList<?>)getCategories()).basicRemove(otherEnd, msgs);
