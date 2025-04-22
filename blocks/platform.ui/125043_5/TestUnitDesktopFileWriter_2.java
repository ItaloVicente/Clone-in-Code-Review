package org.eclipse.urischeme.internal.registration;

import static org.junit.Assert.assertThat;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.hamcrest.core.StringContains;
import org.hamcrest.core.StringEndsWith;
import org.junit.Test;

public class TestUnitDesktopFileWriter {

	private static final String LINE_SEPARATOR = System.getProperty("line.separator"); //$NON-NLS-1$
	private static final String NO_MIME = "";

	@Test
	public void addsOneScheme() {
		DesktopFileWriter writer = getWriterFor(fileContentWith("Exec=/usr/bin/eclipse %u", NO_MIME));

		writer.addScheme("adt", "");

		assertThat(writer.getResult().toString(), containsLine("MimeType=x-scheme-handler/adt;"));
	}

	@Test
	public void addTwoSchemes() {
		DesktopFileWriter writer = getWriterFor(fileContentWith("Exec=/usr/bin/eclipse %u", NO_MIME));

		writer.addScheme("adt", "");
		writer.addScheme("other", "");

		assertThat(writer.getResult().toString(),
				containsLine("MimeType=x-scheme-handler/adt;x-scheme-handler/other;"));
	}

	@Test
	public void addsSecondToExistingScheme() {
		DesktopFileWriter writer = getWriterFor(
				fileContentWith("Exec=/usr/bin/eclipse %u", "MimeType=x-scheme-handler/adt;"));

		writer.addScheme("other", "otherScheme");

		assertThat(writer.getResult().toString(),
				containsLine("MimeType=x-scheme-handler/adt;x-scheme-handler/other;"));
	}

	@Test
	public void doesntAddSchemeIfExisting() {
		DesktopFileWriter writer = getWriterFor(
				fileContentWith("Exec=/usr/bin/eclipse %u", "MimeType=x-scheme-handler/adt;"));

		writer.addScheme("adt", "adtScheme");

		assertThat(writer.getResult().toString(), containsLine("MimeType=x-scheme-handler/adt;"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void addFailsOnIllegalScheme() {
		DesktopFileWriter writer = getWriterFor(
				fileContentWith("Exec=/usr/bin/eclipse %u", "MimeType=x-scheme-handler/adt;"));

		writer.addScheme("&/%", "thisIsIllegal");
	}

	@Test
	public void removesScheme() {
		DesktopFileWriter writer = getWriterFor(
				fileContentWith("Exec=/usr/bin/eclipse %u", "MimeType=x-scheme-handler/adt;"));

		writer.removeScheme("adt");

		assertThat(writer.getResult().toString(), not(contains("MimeType")));
	}

	@Test
	public void removesFirstOfTwoSchemes() {
		DesktopFileWriter writer = getWriterFor(
				fileContentWith("Exec=/usr/bin/eclipse %u", "MimeType=x-scheme-handler/adt;x-scheme-handler/other;"));

		writer.removeScheme("adt");

		assertThat(writer.getResult().toString(), containsLine("MimeType=x-scheme-handler/other;"));
	}

	@Test
	public void removesLastOfTwoSchemes() {
		DesktopFileWriter writer = getWriterFor(
				fileContentWith("Exec=/usr/bin/eclipse %u", "MimeType=x-scheme-handler/adt;x-scheme-handler/other;"));

		writer.removeScheme("other");

		assertThat(writer.getResult().toString(), containsLine("MimeType=x-scheme-handler/adt;"));
	}

	@Test
	public void removesSecondOfThreeSchemes() {
		DesktopFileWriter writer = getWriterFor(fileContentWith("Exec=/usr/bin/eclipse %u",
				"MimeType=x-scheme-handler/adt;x-scheme-handler/other;x-scheme-handler/yetAnother;"));

		writer.removeScheme("other");

		assertThat(writer.getResult().toString(),
				containsLine("MimeType=x-scheme-handler/adt;x-scheme-handler/yetAnother;"));
	}

	@Test
	public void removesNonExistingScheme() {
		DesktopFileWriter writer = getWriterFor(
				fileContentWith("Exec=/usr/bin/eclipse %u", "MimeType=x-scheme-handler/adt;"));

		writer.removeScheme("other");

		assertThat(writer.getResult().toString(), containsLine("MimeType=x-scheme-handler/adt;"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void removeFailsOnIllegalScheme() {
		DesktopFileWriter writer = getWriterFor(
				fileContentWith("Exec=/usr/bin/eclipse %u", "MimeType=x-scheme-handler/adt;"));

		writer.removeScheme("&/%");
	}

	@Test
	public void doesNothing() {
		DesktopFileWriter writer = getWriterFor(
				fileContentWith("Exec=/usr/bin/eclipse %u", "MimeType=x-scheme-handler/adt;"));

		assertThat(writer.getResult().toString(), containsLine("MimeType=x-scheme-handler/adt;"));
	}

	@Test
	public void removesEmptyMimeType() {
		DesktopFileWriter writer = getWriterFor(
				fileContentWith("Exec=/usr/bin/eclipse %u", "MimeType=x-scheme-handler/adt;"));

		writer.removeScheme("adt");

		assertThat(writer.getResult().toString(), not(contains("MimeType")));
	}

	@Test(expected = IllegalStateException.class)
	public void throwsExceptionOnEmptyDocument() {
		getWriterFor("");
	}

	@Test
	public void keepsComments() {
		String comment = "# comment=test\n";
		String fileContent = fileContentWith("Exec=/usr/bin/eclipse %u", NO_MIME);

		DesktopFileWriter writer = getWriterFor(fileContent + comment);

		assertThat(writer.getResult().toString(), new StringEndsWith(comment));
	}

	@Test(expected = IllegalStateException.class)
	public void throwsExceptionOnNonPropertiesFile() {
		getWriterFor("foo=bar");
	}

	@Test
	public void addsUriPlaceholderToExecLineWhenAddingScheme() {
		DesktopFileWriter writer = getWriterFor(fileContentWith("Exec=/usr/bin/eclipse", NO_MIME));

		writer.addScheme("adt", "");

		assertThat(writer.getResult().toString(), containsLine("Exec=/usr/bin/eclipse %u"));
	}

	@Test
	public void addsAddUriPlaceholderToExecLineWhenJustGettingResult() {
		DesktopFileWriter writer = getWriterFor(fileContentWith("Exec=/usr/bin/eclipse", NO_MIME));

		assertThat(writer.getResult().toString(), containsLine("Exec=/usr/bin/eclipse %u"));
	}

	@Test
	public void addsAddUriPlaceholderToExecLineWhenRemovingScheme() {
		DesktopFileWriter writer = getWriterFor(
				fileContentWith("Exec=/usr/bin/eclipse", "MimeType=x-scheme-handler/adt;"));

		writer.removeScheme("adt");

		assertThat(writer.getResult().toString(), containsLine("Exec=/usr/bin/eclipse %u"));
	}

	@Test
	public void keepsPropertiesOrder() {
		String fileContent = fileContentWith("Exec=/usr/bin/eclipse %u", "MimeType=x-scheme-handler/adt;");
		DesktopFileWriter writer = getWriterFor(fileContent);

		assertThat(writer.getResult().toString(), new IsEqual<String>(fileContent + LINE_SEPARATOR));
	}

	private Matcher<String> containsLine(String line) {
		return new StringContains(LINE_SEPARATOR + line + LINE_SEPARATOR);
	}

	private Matcher<String> contains(String line) {
		return new StringContains(line);
	}

	private Matcher<String> not(Matcher<String> not) {
		return new IsNot<String>(not);
	}

	private DesktopFileWriter getWriterFor(String fileContent) {
		InputStream stream = new ByteArrayInputStream(fileContent.getBytes());
		DesktopFileWriter writer = new DesktopFileWriter(stream);
		return writer;
	}

	private String fileContentWith(String execLine, String mimeTypeLine) {
		return "[Desktop Entry]\n" + //
				"Encoding=UTF-8\n" + //
				"Name=Eclipse 4.4.1\n" + //
				"Comment=Eclipse Luna\n" + //
				execLine + "\n" + //
				"Icon=/opt/eclipse/icon.xpm\n" + //
				"Categories=Application;Development;Java;IDE\n" + //
				"Version=1.0\n" + //
				"Type=Application\n" + //
				"Terminal=0\n" + //
				mimeTypeLine;
	}
}
