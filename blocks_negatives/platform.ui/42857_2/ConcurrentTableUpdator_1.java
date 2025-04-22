 * Allows a table to be accessed from a background thread. Provides a table-like public 
 * interface that can accessed from a background thread. As updates arrive from the 
 * background thread, it batches and schedules updates to the real table in the UI thread. 
 * This class can be used with any widget that can be wrapped in the 
 * <code>AbstractVirtualTable</code> interface.  
 * 
