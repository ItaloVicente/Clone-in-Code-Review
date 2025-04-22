
package org.eclipse.core.databinding.observable;

public abstract class AbstractChangeEvent<EV extends AbstractChangeEvent<EV>>
		extends ObservableEvent<EV> {

	private static final long serialVersionUID = -3289969713357065239L;

	static final Object TYPE = new Object();

	public AbstractChangeEvent(IObservable source) {
		super(source);
	}

	protected Object getListenerType() {
		return TYPE;
	}
}
