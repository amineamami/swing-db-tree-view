package tn.iit.glid;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.proteanit.sql.DbUtils;

public class QueryPanelHeader extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel queryLabel;
	private JTextField queryField;
	private JButton executeBtn;
	private QueryPanel panel;

	public QueryPanelHeader(QueryPanel panel) {
		this.panel = panel;
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setHgap(5);
		this.setLayout(flowLayout);
		queryLabel = new JLabel("Query :");
		queryField = new JTextField();
		queryField.setPreferredSize(new Dimension(300, 20));
		executeBtn = new JButton("Execute");
		add(queryLabel);
		add(queryField);
		executeBtn.addActionListener(e -> {
			if (queryField.getText().length() > 0) {
				ResultSet rs = panel.getPanel().getFrame().getLoginPanel().getConnection()
						.executeAnyQuery(queryField.getText());
				if (rs != null) {
					panel.getResultSetTable().setModel(DbUtils.resultSetToTableModel(rs));
					panel.getPlanTable().setModel(DbUtils.resultSetToTableModel(panel.getPanel().getFrame()
							.getLoginPanel().getConnection().getExecutePlan(queryField.getText())));
				} else {
					return;
				}
			}
		});
		add(executeBtn);
		setPreferredSize(new Dimension(500, 30));
		
	}

}
