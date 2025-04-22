						children = ((IAggregateWorkingSet) workingSet).getComponents();
						break;
					case ProjectExplorer.PROJECTS : //$FALL-THROUGH$
					default:
						children = getWorkingSetElements(workingSet);
						break;
