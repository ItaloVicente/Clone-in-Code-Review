				case IWorkbenchPartConstants.PROP_LAST_MODIFIED:
					ISaveablePart3 saveable3 = SaveableHelper.getSaveable3(wrapped);
					if (saveable3 != null) {
						((MDirtyable) part).setLastModified(new Date());
					}
					break;
