
package org.eclipse.egit.core.internal.gitmoji;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.stream.JsonReader;

final class GitmojiJsonReader implements Closeable {

	private static final String GITMOJIS = "gitmojis"; //$NON-NLS-1$

	private static final String EMOJI = "emoji"; //$NON-NLS-1$

	private static final String CODE = "code"; //$NON-NLS-1$

	private static final String DESCRIPTION = "description"; //$NON-NLS-1$

	private final InputStream inputStream;

	private final JsonReader jsonReader;

	GitmojiJsonReader(InputStream inputStream) {
		this.inputStream = inputStream;
		this.jsonReader = new JsonReader(new InputStreamReader(inputStream));
	}

	public List<Gitmoji> read() throws IOException {
		List<Gitmoji> gitmojis = new LinkedList<>();

		jsonReader.beginObject();
		while (jsonReader.hasNext()) {
			if (GITMOJIS.equals(jsonReader.nextName())) {
				readInto(gitmojis);
			}
		}
		jsonReader.endObject();

		return gitmojis;
	}

	private void readInto(List<Gitmoji> gitmojis) throws IOException {
		jsonReader.beginArray();
		while (jsonReader.hasNext()) {
			gitmojis.add(readGitmoji());
		}
		jsonReader.endArray();
	}

	private Gitmoji readGitmoji() throws IOException {
		String emoji = null;
		String code = null;
		String description = null;

		jsonReader.beginObject();
		while (jsonReader.hasNext()) {
			switch (jsonReader.nextName()) {
			case EMOJI:
				emoji = jsonReader.nextString();
				break;

			case CODE:
				code = jsonReader.nextString();
				break;

			case DESCRIPTION:
				description = jsonReader.nextString();
				break;

			default:
				jsonReader.skipValue();
			}
		}
		jsonReader.endObject();

		return new Gitmoji(emoji, code, description);
	}

	@Override
	public void close() throws IOException {
		jsonReader.close();
		inputStream.close();
	}

}
