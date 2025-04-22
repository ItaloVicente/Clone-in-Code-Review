		if(tags != null){
			result.append(", tags: "); //$NON-NLS-1$
			result.append(tags);
		}
		if(contributorURI != null){
			result.append(", contributorURI: "); //$NON-NLS-1$
			result.append(contributorURI);
		}
		if(persistedState != null){
			result.append(", persistedState: "); //$NON-NLS-1$
			result.append(persistedState);
		}
		if(transientData != null && !transientData.isEmpty()){
			result.append(", transientData: "); //$NON-NLS-1$
			result.append(transientData);
		}
