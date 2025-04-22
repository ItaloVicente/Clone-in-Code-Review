			if (contentProvider != null
					&& contentProvider instanceof IMementoAware)
				((IMementoAware) contentProvider).saveState(aMemento);
			if (labelProvider != null && labelProvider instanceof IMementoAware)
				((IMementoAware) labelProvider).saveState(aMemento);

