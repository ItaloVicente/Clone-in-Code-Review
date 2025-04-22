		initialBranch.setContentProvider(ArrayContentProvider.getInstance());
		initialBranch.setLabelProvider(new LabelProvider(){
			@Override
			public String getText(Object element) {
				if (((Ref)element).getName().startsWith(Constants.R_HEADS))
					return ((Ref)element).getName().substring(Constants.R_HEADS.length());
				return ((Ref)element).getName();
			} });
