package tn.iit.glid;

import java.awt.GridLayout;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class QueryPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private QueryPanelHeader header;
	private JScrollPane scrollPaneSet;
	private JScrollPane scrollPanePlan;
	private JLabel resultSetLabel;
	private JTable resultSetTable;
	private JLabel planLabel;
	private JTable planTable;
	private Panel panel;

	public QueryPanel(Panel panel) {

		this.panel = panel;
		GridLayout gridLayout = new GridLayout(5, 1);
		gridLayout.setVgap(-50);
		this.setLayout(gridLayout);
		header = new QueryPanelHeader(this);
		add(header);
		resultSetTable = new JTable(null, new Vector<String>() {
			private static final long serialVersionUID = 1L;
			{
				add("Id");
				add("Value");
			}
		});
		resultSetTable.setAutoscrolls(true);
		resultSetLabel = new JLabel("ResultSet :");
		add(resultSetLabel);
		scrollPaneSet = new JScrollPane(resultSetTable);
		resultSetTable.setFillsViewportHeight(true);
		add(scrollPaneSet);
		planLabel = new JLabel("Plan of execution");
		add(planLabel);
		Vector<String> columnNames = new Vector<String>(3);
		columnNames.addAll(Arrays.asList("Id", "Operation", "Name"));
		planTable = new JTable(null, columnNames);
		planTable.setFillsViewportHeight(true);
		scrollPanePlan = new JScrollPane(planTable);
		add(scrollPanePlan);
	}

	public Panel getPanel() {
		return panel;
	}

	public JTable getResultSetTable() {
		return resultSetTable;
	}

	public JTable getPlanTable() {
		return planTable;
	}
}
