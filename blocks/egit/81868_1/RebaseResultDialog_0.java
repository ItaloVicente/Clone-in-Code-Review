				new CheckoutConflictDialog(shell, repository,
						result.getConflicts()).open();
			});
			return;
		case STOPPED:
		case STASH_APPLY_CONFLICTS:
			break;
		default:
			if (!Activator.getDefault().getPreferenceStore()
					.getBoolean(UIPreferences.SHOW_REBASE_CONFIRM)) {
				return;
