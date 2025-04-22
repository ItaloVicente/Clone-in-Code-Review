package org.eclipse.jgit.lib.objectcheckers;

import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.MutableObjectId;
import org.eclipse.jgit.lib.ObjectChecker;
import org.eclipse.jgit.util.MutableInteger;

import static org.eclipse.jgit.util.RawParseUtils.*;

public class CommitChecker implements IObjectChecker {
    
	public void check(final byte[] raw) throws CorruptObjectException {
		int ptr = 0;

		if ((ptr = match(raw
			throw new CorruptObjectException("no tree header");
		if ((ptr = id(raw
			throw new CorruptObjectException("invalid tree");

		while (match(raw
			ptr += ObjectChecker.parent.length;
			if ((ptr = id(raw
				throw new CorruptObjectException("invalid parent");
		}

		if ((ptr = match(raw
			throw new CorruptObjectException("no author");
		if ((ptr = personIdent(raw
			throw new CorruptObjectException("invalid author");

		if ((ptr = match(raw
			throw new CorruptObjectException("no committer");
		if ((ptr = personIdent(raw
			throw new CorruptObjectException("invalid committer");
	}

    private int id(final byte[] raw
		try {
			new MutableObjectId().fromString(raw
			return ptr + Constants.OBJECT_ID_STRING_LENGTH;
		} catch (IllegalArgumentException e) {
			return -1;
		}
	}

    private int personIdent(final byte[] raw
        final int emailB = nextLF(raw
        if (emailB == ptr || raw[emailB - 1] != '<')
            return -1;

        final int emailE = nextLF(raw
        if (emailE == emailB || raw[emailE - 1] != '>')
            return -1;
        if (emailE == raw.length || raw[emailE] != ' ')
            return -1;

        MutableInteger ptrout=new MutableInteger();
        parseBase10(raw
        ptr = ptrout.value;
        if (emailE + 1 == ptr)
            return -1;
        if (ptr == raw.length || raw[ptr] != ' ')
            return -1;

        parseBase10(raw
        if (ptr + 1 == ptrout.value)
            return -1;
        return ptrout.value;
    }
    
}
