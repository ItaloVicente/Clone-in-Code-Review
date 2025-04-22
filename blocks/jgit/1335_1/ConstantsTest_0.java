
package org.eclipse.jgit.pgm;

import java.text.MessageFormat;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectDatabase;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectStream;
import org.kohsuke.args4j.Argument;

@Command(usage = "usage_catFile")
class CatFile extends TextBuiltin {

	enum CatType {
		BLOB
	}

	@Argument(index = 0
	protected CatType type;

	@Argument(index = 1
	protected String objectName;

	@Override
	protected void run() throws Exception {
		ObjectDatabase objectDatabase = db.getObjectDatabase();
		int typeCode = Constants.typeCode(type.toString().toLowerCase());
		ObjectId objectId = db.resolve(objectName);
		if (objectId == null){
			throw die(MessageFormat.format(CLIText.get().notAValidObjectName
		}
		ObjectLoader open = objectDatabase.open(objectId
		ObjectStream outputStream = open.openStream();
		int n;
		while ((n = outputStream.read()) > 0) {
			out.write(n);
		}
		out.flush();
	}

}
