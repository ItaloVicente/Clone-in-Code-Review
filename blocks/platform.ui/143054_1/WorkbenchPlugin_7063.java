			case BundleEvent.STARTING:
				startingBundles.add(event.getBundle());
				break;
			case BundleEvent.STARTED:
			case BundleEvent.STOPPED:
				startingBundles.remove(event.getBundle());
				break;
			default:
				break;
