package org.eclipse.jgit.diff;

import static org.eclipse.jgit.lib.Constants.OBJ_BLOB;

import java.io.IOException;

import org.eclipse.jgit.attributes.Attribute;
import org.eclipse.jgit.errors.BinaryBlobException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;

public class RawTextHelper {

	public static RawText getRawText(Repository repo
			ObjectReader reader
			Attribute filter) throws IOException
		if (blob.equals(ObjectId.zeroId()))
			return new RawText(new byte[] {});

		if (filter != null && "lfs".equals(filter.getValue())) {
			try {
				RawTextProvider f = (RawTextProvider) Class
						.newInstance();
				return f.compute(repo
			} catch (ClassNotFoundException | IllegalAccessException
					| InstantiationException e) {
			}
		}

		return new RawText(reader.open(blob
	}

	@FunctionalInterface
	public interface RawTextProvider {

		public RawText compute(Repository repo
				ObjectReader reader)
				throws IOException

	}

}
