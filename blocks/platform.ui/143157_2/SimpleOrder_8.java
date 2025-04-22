
package org.eclipse.core.internal.databinding.property.list;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.observable.ObservableTracker;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.property.list.IListProperty;
import org.eclipse.core.databinding.property.list.ListProperty;
import org.eclipse.core.internal.databinding.property.PropertyObservableUtil;

public class ListPropertyDetailMultiList<S, E1, E2> extends ListProperty<S, E2> {
	private final ListProperty<S, E1> masterProperty;
	private final IListProperty<? super E1, E2> detailProperty;

	public ListPropertyDetailMultiList(ListProperty<S, E1> masterProperty,
			IListProperty<? super E1, E2> detailProperty) {
		this.masterProperty = masterProperty;
		this.detailProperty = detailProperty;
	}

	@Override
	public Object getElementType() {
		return detailProperty.getElementType();
	}

	@Override
	protected List<E2> doGetList(S source) {
		List<E1> masterList = masterProperty.getList(source);
		List<E2> detailList = new ArrayList<>();
		for (E1 elem : masterList) {
			detailList.addAll(detailProperty.getList(elem));
		}
		return detailList;
	}

	@Override
	public IObservableList<E2> observe(Realm realm, S source) {
		IObservableList<E1> masterList;

		ObservableTracker.setIgnore(true);
		try {
			masterList = masterProperty.observe(realm, source);
		} finally {
			ObservableTracker.setIgnore(false);
		}

		IObservableList<E2> detailList = detailProperty.observeDetail(masterList);
		PropertyObservableUtil.cascadeDispose(detailList, masterList);
		return detailList;
	}

	@Override
	public String toString() {
		return masterProperty + " => " + detailProperty; //$NON-NLS-1$
	}
}
