		case MIXED:
			resetIndex();
			monitor.worked(1);
			writeIndex();
			monitor.worked(3);
			break;
