import java.awt.LayoutManager;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.util.HashMap;
import java.util.Map;

public class PanelTable extends JTable {

	public void fillData(Map<String, Map<String, String>> data, String[] cols, int[] colsToHide) {
		String[] idx = data.keySet().toArray(new String[data.keySet().size()]);
		DefaultTableModel tableModel = new DefaultTableModel(cols, 0);
		this.setModel(tableModel);
		if (cols.length > 0) {
			for (int col: colsToHide) {
				this.getColumnModel().getColumn(col).setMinWidth(0);
				this.getColumnModel().getColumn(col).setMaxWidth(0);
				this.getColumnModel().getColumn(col).setWidth(0);
			}
		}
		for (String k: idx) {
			Map<String, String> row = data.get(k);
			String[] vals = new String[cols.length];
			for (int i = 0; i < cols.length; i++) {
				vals[i] = row.get(cols[i]);
			}
			tableModel.addRow(vals);
		}
	}
	
	public void fillData(Map<String, Map<String, String>> data, String cols[]) {
		this.fillData(data, cols, new int[]{0});
	}
	
	public void fillData(Map<String, Map<String, String>> data, int[] colsToHide) {
		Map<String, String> row = data.get(data.keySet().toArray()[0]); 
		this.fillData(data, row.keySet().toArray(new String[row.size()]), colsToHide);
	}
	
	public void fillData(Map<String, Map<String, String>> data) {
		Map<String, String> row = data.get(data.keySet().toArray()[0]); 
		this.fillData(data, row.keySet().toArray(new String[row.size()]));
	}

}
