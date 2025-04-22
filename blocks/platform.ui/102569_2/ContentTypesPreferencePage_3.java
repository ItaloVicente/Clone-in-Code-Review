			for (String prePatternFileSpec : prePatternFileSpecs) {
				Spec spec = new Spec(prePatternFileSpec, IContentType.FILE_PATTERN_SPEC, true, 5);
				returnValues.add(spec);
			}

			return returnValues.toArray(new Spec[returnValues.size()]);
