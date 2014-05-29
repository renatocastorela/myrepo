package org.oiga.estructura.analysis;

import java.util.List;
import java.util.Map;
@Deprecated
public interface AnalysisEngine<T> {
	public List<T> analyse(List<Map<String,String>> data);
}
