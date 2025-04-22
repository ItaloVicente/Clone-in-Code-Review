
		try {
			objectsLists[object.getType()].add(otp);
		} catch (ArrayIndexOutOfBoundsException x) {
			throw new IncorrectObjectTypeException(object,
					JGitText.get().incorrectObjectType_COMMITnorTREEnorBLOBnorTAG);
		} catch (UnsupportedOperationException x) {
			throw new IncorrectObjectTypeException(object,
					JGitText.get().incorrectObjectType_COMMITnorTREEnorBLOBnorTAG);
		}
