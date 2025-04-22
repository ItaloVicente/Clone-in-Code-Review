    public IObjectChecker checkerFor(int objType) throws CorruptObjectException {
        if (!map.containsKey(objType)) {
            throw new CorruptObjectException(MessageFormat.format(JGitText.get().corruptObjectInvalidType2
        }
        return map.get(objType);
    }
