			case 'g':
				return new GroupMarker("testGroup" + groupMarkerCount++);
			case 's':
				return new Separator("testSeparator" + separatorCount++);
			case 'a': {
				IAction action = new DummyAction();
				return new ActionContributionItem(action);
			}
			case 'n': {
				IAction action = new DummyAction();
				ActionContributionItem item = new ActionContributionItem(action);
				item.setVisible(false);
				return item;
			}
			default:
				throw new IllegalArgumentException("Unknown template char: " + template);
