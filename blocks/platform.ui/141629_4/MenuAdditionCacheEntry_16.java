			if (null != itemType) switch (itemType) {
		    	case IWorkbenchRegistryConstants.TAG_COMMAND:{
			    MToolBarElement element = createToolBarCommandAddition(child);
			    toolBarContribution.getChildren().add(element);
				break;
			    }
		    	case IWorkbenchRegistryConstants.TAG_SEPARATOR:{
			    MToolBarElement element = createToolBarSeparatorAddition(child);
			    toolBarContribution.getChildren().add(element);
				break;
			    }
		    	case IWorkbenchRegistryConstants.TAG_CONTROL:{
			    MToolBarElement element = createToolControlAddition(child);
			    toolBarContribution.getChildren().add(element);
				break;
			    }
		    	case IWorkbenchRegistryConstants.TAG_DYNAMIC:{
			    ContextFunction generator = new ContextFunction() {
				@Override
				public Object compute(IEclipseContext context, String contextKey) {
				    ServiceLocator sl = new ServiceLocator();
				    sl.setContext(context);
				    DynamicToolBarContributionItem dynamicItem = new DynamicToolBarContributionItem(
					    MenuHelper.getId(child), sl, child);
				    return dynamicItem;
				}
			    };
			    MToolBarElement element = createToolDynamicAddition(child);
			    RenderedElementUtil.setContributionManager(element, generator);
			    toolBarContribution.getChildren().add(element);
				break;
			    }
		    	default:
			    break;
		    }
