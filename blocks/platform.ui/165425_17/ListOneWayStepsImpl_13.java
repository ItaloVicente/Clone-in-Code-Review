package org.eclipse.core.internal.databinding.bind;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.core.databinding.observable.value.IObservableValue;

class GenerateSourceFilesForListsAndSets {
	public static void main(String[] args) throws IOException {
		String originalVariant = "Value"; //$NON-NLS-1$
		List<String> derivedVariants = Arrays.asList("List", "Set"); //$NON-NLS-1$//$NON-NLS-2$

		List<String> files = Arrays.asList(
				"src/org/eclipse/core/databinding/bind/steps/?CommonSteps.java", //$NON-NLS-1$
				"src/org/eclipse/core/databinding/bind/steps/?OneWaySteps.java", //$NON-NLS-1$
				"src/org/eclipse/core/databinding/bind/steps/?TwoWaySteps.java", //$NON-NLS-1$
				"src/org/eclipse/core/internal/databinding/bind/?OneWayStepsImpl.java", //$NON-NLS-1$
				"src/org/eclipse/core/internal/databinding/bind/?TwoWayStepsImpl.java"); //$NON-NLS-1$

		for (String variant : derivedVariants) {
			for (String file : files) {
				Path source = Paths.get(file.replace("?", originalVariant)); //$NON-NLS-1$
				Path dest = Paths.get(file.replace("?", variant)); //$NON-NLS-1$

				String contents = new String(Files.readAllBytes(source), StandardCharsets.UTF_8);

				contents = filterMethodDecl(contents, "validateTwoWay"); //$NON-NLS-1$
				contents = filterMethodDecl(contents, "validateAfterConvert"); //$NON-NLS-1$
				contents = filterMethodImpl(contents, "validateAfterConvert"); //$NON-NLS-1$
				contents = filterMethodDecl(contents, "validateAfterGet"); //$NON-NLS-1$
				contents = filterMethodImpl(contents, "validateAfterGet"); //$NON-NLS-1$
				contents = filterMethodDecl(contents, "validateBeforeSet"); //$NON-NLS-1$
				contents = filterMethodImpl(contents, "validateBeforeSet"); //$NON-NLS-1$
				contents = filterMethodDecl(contents, "convertOnly"); //$NON-NLS-1$
				contents = filterMethodImpl(contents, "convertOnly"); //$NON-NLS-1$
				contents = filterMethodImpl(contents, "validateTwoWay"); //$NON-NLS-1$
				contents = contents.replace(originalVariant, variant);
				contents = contents.replace(originalVariant.toLowerCase(), variant.toLowerCase());

				Files.write(dest, contents.getBytes(StandardCharsets.UTF_8));
				System.out.println("Wrote '" + dest); //$NON-NLS-1$
			}
		}
	}

	private static final String DOC_PATTERN = "\\t*/\\*[^*]*\\*+(?:[^/*][^*]*\\*+)*/"; //$NON-NLS-1$

	private static String filterMethodDecl(String source, String methodName) {
		return source.replaceAll("(?sm)" + DOC_PATTERN + "[\\s@\\w<>]+ " + methodName + "[^\\n]*?;\\n*", ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	}

	private static String filterMethodImpl(String source, String methodName) {
		return source.replaceAll("(?sm)(" + DOC_PATTERN + ")?[\\s@\\w<>]+ " + methodName + ".*?\\}\\n*", "\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	}
}
