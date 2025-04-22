					String depBundleId = dependentBundle.getSymbolicName();
					Collection<String> depBundleReqs = requires.get(depBundleId);
					depBundleReqs.add(bundleId);
					Collection<String> bundleDeps = depends.get(bundleId);
					assert bundleDeps != null;
					bundleDeps.add(depBundleId);
