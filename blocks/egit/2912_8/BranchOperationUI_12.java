		case MODE_DELETE:
			new DeleteBranchDialog(getShell(), repository).open();
			return null;
		case MODE_RENAME:
			new RenameBranchDialog(getShell(), repository).open();
			return null;
