		public boolean matches(String refPath) {
			IMatcher matcher = patternWithExpandedMacros();
			if(matcher == null){
				return false;
			}
			return matcher.matches(refPath, false, true);
