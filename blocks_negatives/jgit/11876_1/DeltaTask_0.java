			for (Slice s = firstSlice; s != null; s = block.stealWork()) {
				dw = new DeltaWindow(block.config, block.dc, or, block.pm,
						block.list, s.beginIndex, s.endIndex);
				dw.search();
				dw = null;
			}
