				break;
			case FAILED:
				IStatus details = getErrorList(cherryPickResult.getFailingPaths());
				Activator.showErrorStatus(
						UIText.CherryPickHandler_CherryPickFailedMessage, details);
				break;
			case OK:
				break;
