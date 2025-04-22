
package org.eclipse.e4.ui.workbench;

import java.util.HashMap;
import java.util.Map;

public class UIEventBuilder {
	private String topic;
	private Map<String, Object> params;

	public UIEventBuilder(String topic) {
		this.topic = topic;
		params = new HashMap<String, Object>();
	}

	public static UIEventBuilder createEvent(String topic) {
		return new UIEventBuilder(topic);
	}

	public UIEventBuilder withParam(String name, Object value) {
		params.put(name, value);
		return this;
	}

	public String getTopic() {
		return topic;
	}

	public Map<String, Object> getParams() {
		return params;
	}
}
