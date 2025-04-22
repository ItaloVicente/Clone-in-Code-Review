		boolean advertiseRefInWant = transferConfig.isAllowRefInWant() &&
				db.getConfig().getBoolean("uploadpack", null, //$NON-NLS-1$
						"advertiserefinwant", true); //$NON-NLS-1$
		caps.add(
				COMMAND_FETCH + '=' +
				OPTION_SHALLOW);
