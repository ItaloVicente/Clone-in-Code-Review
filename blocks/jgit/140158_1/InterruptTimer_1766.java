
package org.eclipse.jgit.util.io;

import java.io.InputStream;
import java.io.OutputStream;

import org.eclipse.jgit.attributes.Attributes;
import org.eclipse.jgit.lib.CoreConfig.EolStreamType;
import org.eclipse.jgit.treewalk.TreeWalk.OperationType;
import org.eclipse.jgit.treewalk.WorkingTreeOptions;
import org.eclipse.jgit.util.SystemReader;

public final class EolStreamTypeUtil {

	private EolStreamTypeUtil() {
	}

	public static EolStreamType detectStreamType(OperationType op
			WorkingTreeOptions options
		switch (op) {
		case CHECKIN_OP:
			return checkInStreamType(options
		case CHECKOUT_OP:
			return checkOutStreamType(options
		default:
		}
	}

	public static InputStream wrapInputStream(InputStream in
			EolStreamType conversion) {
		switch (conversion) {
		case TEXT_CRLF:
			return new AutoCRLFInputStream(in
		case TEXT_LF:
			return new AutoLFInputStream(in
		case AUTO_CRLF:
			return new AutoCRLFInputStream(in
		case AUTO_LF:
			return new AutoLFInputStream(in
		default:
			return in;
		}
	}

	public static OutputStream wrapOutputStream(OutputStream out
			EolStreamType conversion) {
		switch (conversion) {
		case TEXT_CRLF:
			return new AutoCRLFOutputStream(out
		case AUTO_CRLF:
			return new AutoCRLFOutputStream(out
		case TEXT_LF:
			return new AutoLFOutputStream(out
		case AUTO_LF:
			return new AutoLFOutputStream(out
		default:
			return out;
		}
	}

	private static EolStreamType checkInStreamType(WorkingTreeOptions options
			Attributes attrs) {
			return EolStreamType.DIRECT;
		}

		}

		if (eol != null)
			return EolStreamType.TEXT_LF;

			return EolStreamType.TEXT_LF;
		}

			return EolStreamType.AUTO_LF;
		}

		switch (options.getAutoCRLF()) {
		case TRUE:
		case INPUT:
			return EolStreamType.AUTO_LF;
		case FALSE:
			return EolStreamType.DIRECT;
		}

		return EolStreamType.DIRECT;
	}

	private static EolStreamType getOutputFormat(WorkingTreeOptions options) {
		switch (options.getAutoCRLF()) {
		case TRUE:
			return EolStreamType.TEXT_CRLF;
		default:
		}
		switch (options.getEOL()) {
		case CRLF:
			return EolStreamType.TEXT_CRLF;
		case NATIVE:
			if (SystemReader.getInstance().isWindows()) {
				return EolStreamType.TEXT_CRLF;
			}
			return EolStreamType.TEXT_LF;
		case LF:
		default:
			break;
		}
		return EolStreamType.DIRECT;
	}

	private static EolStreamType checkOutStreamType(WorkingTreeOptions options
			Attributes attrs) {
			return EolStreamType.DIRECT;
		}

		}

		if (eol != null) {
				return EolStreamType.TEXT_CRLF;
				return EolStreamType.DIRECT;
			}
		}
			return getOutputFormat(options);
		}

			EolStreamType basic = getOutputFormat(options);
			switch (basic) {
			case TEXT_CRLF:
				return EolStreamType.AUTO_CRLF;
			case TEXT_LF:
				return EolStreamType.AUTO_LF;
			default:
				return basic;
			}
		}

		switch (options.getAutoCRLF()) {
		case TRUE:
			return EolStreamType.AUTO_CRLF;
		default:
		}

		return EolStreamType.DIRECT;
	}

}
