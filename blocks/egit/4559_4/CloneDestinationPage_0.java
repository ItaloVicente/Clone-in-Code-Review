				if (((Ref) element).getName().startsWith(Constants.R_HEADS))
					return ((Ref) element).getName().substring(
							Constants.R_HEADS.length());
				return ((Ref) element).getName();
			}
		});
