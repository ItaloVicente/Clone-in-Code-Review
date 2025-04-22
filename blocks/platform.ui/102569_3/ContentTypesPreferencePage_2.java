				Spec spec = new Spec(preextfileSpec, IContentType.FILE_EXTENSION_SPEC, true, 3);
				returnValues.add(spec);
			}

			for (String userPatternFileSpec : userPatternFileSpecs) {
				Spec spec = new Spec(userPatternFileSpec, IContentType.FILE_PATTERN_SPEC, false, 4);
