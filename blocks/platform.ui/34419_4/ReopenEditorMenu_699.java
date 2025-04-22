package org.eclipse.ui.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ReferenceCounter {
    private Map mapIdToRec = new HashMap(11);

    public class RefRec {
        public RefRec(Object id, Object value) {
            this.id = id;
            this.value = value;
            addRef();
        }

        public Object getId() {
            return id;
        }

        public Object getValue() {
            return value;
        }

        public int addRef() {
            ++refCount;
            return refCount;
        }

        public int removeRef() {
            --refCount;
            return refCount;
        }

        public int getRef() {
            return refCount;
        }

        public boolean isNotReferenced() {
            return (refCount <= 0);
        }

        public Object id;

        public Object value;

        private int refCount;
    }

    public ReferenceCounter() {
        super();
    }

    public int addRef(Object id) {
        RefRec rec = (RefRec) mapIdToRec.get(id);
        if (rec == null) {
			return 0;
		}
        return rec.addRef();
    }

    public Object get(Object id) {
        RefRec rec = (RefRec) mapIdToRec.get(id);
        if (rec == null) {
			return null;
		}
        return rec.getValue();
    }

    public Set keySet() {
        return mapIdToRec.keySet();
    }

    public void put(Object id, Object value) {
        RefRec rec = new RefRec(id, value);
        mapIdToRec.put(id, rec);
    }

    public int getRef(Object id) {
        RefRec rec = (RefRec) mapIdToRec.get(id);
        if (rec == null) {
			return 0;
		}
        return rec.refCount;
    }

    public int removeRef(Object id) {
    	RefRec rec = (RefRec) mapIdToRec.get(id);
    	if (rec == null) {
    		return 0;
    	}
    	int newCount = rec.removeRef();
    	if (newCount <= 0) {
    		mapIdToRec.remove(id);
    	}
    	return newCount;
    }
    
    public List values() {
        int size = mapIdToRec.size();
        ArrayList list = new ArrayList(size);
        Iterator iter = mapIdToRec.values().iterator();
        while (iter.hasNext()) {
            RefRec rec = (RefRec) iter.next();
            list.add(rec.getValue());
        }
        return list;
    }
}
