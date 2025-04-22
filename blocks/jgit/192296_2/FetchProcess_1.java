	private boolean matchNegativeRefSpec(Ref r){
		for(RefSpec spec : negativeSrcRefSpecs){
			if(spec.matchSource(r)){
				return true;
			}
		}

		for(RefSpec spec : negativeDestRefSpecs){
			if(spec.matchDestination(r)){
				return true;
			}
		}

		return false;
	}

