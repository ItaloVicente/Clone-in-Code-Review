	private void establishBackwardCompatibility(){
		for( Iterator<MTrimContribution> iter = application.getTrimContributions().iterator(); iter.hasNext(); ){
			MTrimContribution contribution = iter.next();
			if ("org.eclipse.ui.ide.application.trimcontribution.QuickAccess".contains(contribution.getElementId())) { //$NON-NLS-1$
				iter.remove();
			}
		}
	}

