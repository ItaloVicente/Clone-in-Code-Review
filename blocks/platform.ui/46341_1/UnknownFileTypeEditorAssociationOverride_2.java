package org.eclipse.ui.internal.ide.misc;

import java.io.IOException;
import java.io.InputStream;

import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;

public class TextFileDetector {

	public static boolean isTextFile(InputStream is) throws IOException {
		CharsetDetector detector = new CharsetDetector();
		detector.setText(is);
		CharsetMatch match = detector.detect();

		return match.getConfidence() >= 10;
	}

}

