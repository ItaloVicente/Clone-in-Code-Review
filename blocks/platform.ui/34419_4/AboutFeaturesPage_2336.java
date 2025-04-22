package org.eclipse.ui.internal.about;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AboutFeaturesButtonManager {
    private Map providerMap = new HashMap();

    private static class Key {
        public String providerName;

        public Long crc;

        public Key(String providerName, Long crc) {
            this.providerName = providerName;
            this.crc = crc;
        }

        @Override
		public boolean equals(Object o) {
            if (!(o instanceof Key)) {
				return false;
			}
            Key other = (Key) o;
            if (!providerName.equals(other.providerName)) {
				return false;
			}
            return crc.equals(other.crc);
        }

        @Override
		public int hashCode() {
            return providerName.hashCode();
        }
    }

    public boolean add(AboutBundleGroupData info) {
        Long crc = info.getFeatureImageCrc();
        if (crc == null) {
			return false;
		}

        String providerName = info.getProviderName();
        Key key = new Key(providerName, crc);

        List infoList = (List) providerMap.get(key);
        if (infoList != null) {
            infoList.add(info);
            return false;
        }

        infoList = new ArrayList();
        infoList.add(info);
        providerMap.put(key, infoList);
        return true;
    }

    public AboutBundleGroupData[] getRelatedInfos(AboutBundleGroupData info) {
        Long crc = info.getFeatureImageCrc();
        if (crc == null) {
			return new AboutBundleGroupData[0];
		}

        String providerName = info.getProviderName();
        Key key = new Key(providerName, crc);

        List infoList = (List) providerMap.get(key);
        if (infoList == null) {
			return new AboutBundleGroupData[0];
		}

        return (AboutBundleGroupData[]) infoList
                .toArray(new AboutBundleGroupData[0]);
    }
}
