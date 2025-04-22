package org.eclipse.ui.internal.views.properties.tabbed.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;

public class TabbedPropertyRegistryFactory {

	class CacheData {
		TabbedPropertyRegistry registry;
		List references;
	}

	private static TabbedPropertyRegistryFactory INSTANCE = new TabbedPropertyRegistryFactory();

	public static TabbedPropertyRegistryFactory getInstance() {
		return INSTANCE;
	}

	private TabbedPropertyRegistryFactory() {
		super();
		idToCacheData = new HashMap();
	}

	protected Map idToCacheData; // cache

	public TabbedPropertyRegistry createRegistry(
			ITabbedPropertySheetPageContributor target) {
		String key = target.getContributorId();
		CacheData data = (CacheData) idToCacheData.get(key);
		if (data == null) {
			data = new CacheData();
			data.registry = new TabbedPropertyRegistry(key);
			data.references = new ArrayList(5);
			idToCacheData.put(key, data);
		}
		data.references.add(target);
		return data.registry;
	}

	public void disposeRegistry(ITabbedPropertySheetPageContributor target) {
		String key = target.getContributorId();
		CacheData data = (CacheData) idToCacheData.get(key);
		if (data != null) {
			data.references.remove(target);
			if (data.references.isEmpty()) {
				data.registry.dispose();
				idToCacheData.remove(key);
			}
		}
	}
}
