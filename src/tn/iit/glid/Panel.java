package tn.iit.glid;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreeSelectionModel;

import net.proteanit.sql.DbUtils;
import tn.iit.glid.database.DBConnection;

public class Panel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTree tree;
	private JScrollPane treeView;
	private DefaultTreeModel treeModel;
	private MainFrame frame;
	private QueryPanel queryPanel;

	public Panel(MainFrame frame) {
		this.frame = frame;
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setResizeWeight(0.5);
		setLayout(new BorderLayout());
		add(splitPane, BorderLayout.CENTER);

		DBConnection connection = frame.getLoginPanel().getConnection();
		List<String> tablesList = connection.getTables();
		List<String> viewsList = connection.getViews();
		List<String> indexesList = connection.getIndexes();
		List<String> sequencesList = connection.getSequences();
		List<String> proceduresList = connection.getProcedures();
		List<String> triggersList = connection.getTriggers();

		DefaultMutableTreeNode root = new DefaultMutableTreeNode(
				frame.getLoginPanel().getUserField().getText().toUpperCase());
		treeModel = new DefaultTreeModel(root);

		// insertion des tables;
		DefaultMutableTreeNode tables = new DefaultMutableTreeNode("Tables");
		tablesList.forEach(table -> tables.insert(new DefaultMutableTreeNode(table), tables.getChildCount()));
		treeModel.insertNodeInto(tables, root, 0);
		// insertion des views
		DefaultMutableTreeNode views = new DefaultMutableTreeNode("Views");
		viewsList.forEach(view -> views.insert(new DefaultMutableTreeNode(view), views.getChildCount()));
		treeModel.insertNodeInto(views, root, 1);
		// insertion des indexes
		DefaultMutableTreeNode indexes = new DefaultMutableTreeNode("Index");
		indexesList.forEach(index -> indexes.insert(new DefaultMutableTreeNode(index), indexes.getChildCount()));
		treeModel.insertNodeInto(indexes, root, 2);
		// insertion des sequences
		DefaultMutableTreeNode sequences = new DefaultMutableTreeNode("Sequences");
		sequencesList.forEach(seq -> sequences.insert(new DefaultMutableTreeNode(seq), sequences.getChildCount()));
		treeModel.insertNodeInto(sequences, root, 3);
		// insertion des procedures
		DefaultMutableTreeNode procedures = new DefaultMutableTreeNode("Procedures");
		proceduresList.forEach(proc -> {
			if (proc != null)
				procedures.insert(new DefaultMutableTreeNode(proc), procedures.getChildCount());
			else {
				procedures.add(new DefaultMutableTreeNode("no procedures found"));
				return;
			}
		});
		treeModel.insertNodeInto(procedures, root, 4);
		// insertion des triggers;
		DefaultMutableTreeNode triggers = new DefaultMutableTreeNode("Triggers");
		triggersList.forEach(trig -> {
			if (trig != null)
				triggers.insert(new DefaultMutableTreeNode(trig), triggers.getChildCount());
		});
		treeModel.insertNodeInto(triggers, root, 5);

		ImageIcon imageIcon = new ImageIcon("res/tableIcon.png");
		DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
		renderer.setLeafIcon(imageIcon);
		
		JTree tree = new JTree(treeModel);
		tree.setRootVisible(true);
		tree.setShowsRootHandles(true);
		tree.putClientProperty("JTree.lineStyle", "Angled");
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setCellRenderer(renderer);
		tree.addTreeSelectionListener(e -> {
			String nodeText = "";
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

			if (node == null)
				return;
			if (!node.equals(null) && node.getParent() != null) {
				nodeText = node.getParent().toString();
			}
			if (nodeText.equals("Tables")) {
				if (node.getChildCount() == 0) {
					connection.getTableColumns(node.toString())
							.forEach(c -> node.insert(new DefaultMutableTreeNode(c), node.getChildCount()));
					DefaultMutableTreeNode constraints = new DefaultMutableTreeNode("Constraints");
					List<String> constList = connection.getTableConstraints(node.toString());
					constList.forEach(
							c -> constraints.insert(new DefaultMutableTreeNode(c), constraints.getChildCount()));
					node.insert(constraints, node.getChildCount());
				}
				queryPanel.getResultSetTable().setModel(DbUtils
						.resultSetToTableModel(frame.getLoginPanel().getConnection().getTableData(node.toString())));
			} else if (nodeText.equals("Views")) {
				if (node.getChildCount() == 0) {
					connection.getViewColumns(node.toString())
							.forEach(v -> node.insert(new DefaultMutableTreeNode(v), node.getChildCount()));
				}
			}

		});
		treeView = new JScrollPane(tree);
		treeView.setPreferredSize(new Dimension(200, HEIGHT));
		queryPanel = new QueryPanel(this);
		splitPane.setLeftComponent(treeView);
		splitPane.setRightComponent(queryPanel);
		splitPane.setDividerLocation(0.3);

	}

	public MainFrame getFrame() {
		return frame;
	}
}
