        final KeySequenceBinding castedObject = (KeySequenceBinding) object;
        if (!Util.equals(keySequence, castedObject.keySequence)) {
            return false;
        }

        return Util.equals(match, castedObject.match);
    }
